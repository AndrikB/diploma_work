package org.blagij.diploma.common

import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.impl.RouterImpl
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.reflect

class WebRouter(private val vertx: Vertx) : RouterImpl(vertx) {

    private val jsonMapper = ObjectMapper().registerModule(ResponseJacksonSerializers())
    private val log = logger(this::class)

    fun route(address: String, handler: suspend (RoutingContext) -> Any?): Route {
        val route: Route = if (address.contains(' ')) {
            with(address.split(' ')) {
                val method = HttpMethod.valueOf(first())
                val route = route(method, last())

                // Register body handler
                if (listOf(HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT).contains(method)) {
                    route.handler(
                        BodyHandler.create(true)
                            .setDeleteUploadedFilesOnEnd(false)
                            .setBodyLimit(-1)
                    )
                }

                route
            }
        } else {
            route(address)
        }

        return route.handler { ctx ->
            GlobalScope.launch(vertx.dispatcher()) {
                try {
                    val result = handler(ctx)
                    val response = ctx.response()

                    response.putHeader("Content-Type", "application/json")

                    when (result) {
                        null -> response.end()
                        is String -> response.end(result)
                        else -> response.end(jsonMapper.writeValueAsString(result))
                    }


                } catch (e: Throwable) {
                    log.error("Route '$address' exception", e)

                    ctx.response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(500)
                        .end(e.message ?: "Something went wrong")
                }
            }
        }

    }

    operator fun String.invoke(
        successCode: Int = 200,
        handler: suspend () -> Any?
    ): Route =
        route(this) { ctx: RoutingContext ->
            ctx.response().statusCode = successCode
            handler().takeIf { successCode == 200 }
        }


    inline operator fun <reified A> String.invoke(
        successCode: Int = 200,
        noinline handler: suspend (A) -> Any?
    ): Route {
        return route(this) { ctx ->
            ctx.response().statusCode = successCode

            val params = handler.reflect()!!.parameters.map(ParameterValueResolver(ctx)::resolve)
            handler(params[0] as A).takeIf { successCode == 200 }
        }
    }
}