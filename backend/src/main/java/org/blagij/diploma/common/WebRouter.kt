package org.blagij.diploma.common

import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.impl.RouterImpl
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.*

class WebRouter(private val vertx: Vertx) : RouterImpl(vertx) {

    private val jsonMapper = ObjectMapper()

    private fun route(address: String, handler: suspend (RoutingContext) -> Any?): Route {
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
                        else -> response.end(jsonMapper.writeValueAsString(result))
                    }


                } catch (e: Throwable) {
                    println("Route '$address' exception $e")

                    ctx.response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(500)
                        .end(e.message)
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

}