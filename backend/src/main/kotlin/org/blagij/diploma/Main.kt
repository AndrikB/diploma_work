package org.blagij.diploma

import io.vertx.core.Future
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.ext.mail.MailMessage
import io.vertx.kotlin.core.http.httpServerOptionsOf
import org.blagij.diploma.common.WebRouter
import org.blagij.diploma.common.codec.MailMessageCodec
import org.blagij.diploma.common.logger
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.allInstances
import org.kodein.di.generic.instance
import java.util.concurrent.atomic.AtomicInteger

val log = logger("Main")

fun main() {
    val vertx = kodein.direct.instance<Vertx>()

    val mapper = DatabindCodec.mapper()
    vertx.eventBus().registerDefaultCodec(MailMessage::class.java, MailMessageCodec(mapper))

    setupVerticles(kodein.direct.allInstances(), vertx, kodein)
}

fun setupVerticles(verticles: List<Verticle>, vertx: Vertx, kodein: Kodein) {
    vertx.deploy(verticles).onSuccess {
        vertx.createHttpServer(httpServerOptionsOf(compressionSupported = true))
            .requestHandler(kodein.direct.instance<WebRouter>()).listen(8080) { listen ->
                if (listen.succeeded()) {
                    log.info("HTTP server running on port 8080")
                } else {
                    log.error("Failed to start HTTP server on port 8080, '${listen.cause()}'")
                    vertx.close()
                }
            }
    }.onFailure {
        log.error("Failed to deploy verticle")
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

                log.info("Deployed: ${verticle::class.simpleName}")
                if (counter.decrementAndGet() == 0) {
                    deploy.complete()
                }
            }
        }
    }
}
