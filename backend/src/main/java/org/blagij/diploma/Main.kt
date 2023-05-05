package org.blagij.diploma

import io.vertx.core.Future
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.http.httpServerOptionsOf
import org.blagij.diploma.common.WebRouter
import org.blagij.diploma.common.kodein
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.allInstances
import org.kodein.di.generic.instance
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    println("hello world!")

    val vertx = kodein.direct.instance<Vertx>()

    setupVerticles(kodein.direct.allInstances(), vertx, kodein)

}

fun setupVerticles(verticles: List<Verticle>, vertx: Vertx, kodein: Kodein) {
    vertx.deploy(verticles).onSuccess {

        vertx
            .createHttpServer(
                httpServerOptionsOf(
                    compressionSupported = true
                )
            )
            .requestHandler(kodein.direct.instance<WebRouter>())
            .listen(8080) { listen ->
                if (listen.succeeded()) {
                    println("HTTP server running on port 8080")
                } else {
                    println("Failed to start HTTP server on port 8080, '${listen.cause()}'")
                }
            }
    }.onFailure {
        println("Failed to deploy verticle")
        vertx.close()
    }
}


fun Vertx.deploy(verticles: List<Verticle>): Future<Unit> {
    return Future.future { deploy ->
        val counter = AtomicInteger(verticles.size)

        verticles.forEachIndexed { _, verticle ->
            deployVerticle(verticle) {

                if (it.failed()) {
                    deploy.fail(it.cause())
                }

                println("Deployed: ${verticle::class.simpleName}")

                if (counter.decrementAndGet() == 0) {
                    deploy.complete()
                }
            }
        }
    }
}
