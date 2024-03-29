package org.blagij.diploma

import io.vertx.core.CompositeFuture
import io.vertx.core.Handler
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.ext.mail.MailMessage
import io.vertx.kotlin.core.http.httpServerOptionsOf
import org.blagij.diploma.common.WebRouter
import org.blagij.diploma.common.codec.MailMessageCodec
import org.blagij.diploma.common.logger
import org.blagij.diploma.service.auth.CleanUpTokensPeriodic
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.allInstances
import org.kodein.di.generic.instance
import java.time.Duration
import java.time.LocalTime.*

val log = logger("Main")

fun main() {
    val vertx = kodein.direct.instance<Vertx>()

    setupVerticles(kodein.direct.allInstances(), vertx, kodein)
    setupPeriodics(vertx)
    registerCodecs(vertx)
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


fun Vertx.deploy(verticles: List<Verticle>): CompositeFuture {
    return CompositeFuture.all(verticles.map {
        deployVerticle(it).onSuccess {
            log.info("Deployed: ${it::class.simpleName}")
        }
    })
}

fun getListOfPeriodics(): List<Handler<Long>> {
    return listOf(
        kodein.direct.instance<CleanUpTokensPeriodic>()
    )
}

fun setupPeriodics(vertx: Vertx) {
    val timeToMidnight = (of(3, 0).toSecondOfDay() - now().toSecondOfDay()).mod(86400L)
    getListOfPeriodics().forEach { periodic ->
        vertx.setPeriodic(timeToMidnight, Duration.ofDays(1).toMillis()) { periodic.handle(it) }
    }
}

fun registerCodecs(vertx: Vertx) {
    val mapper = DatabindCodec.mapper()
    vertx.eventBus().registerDefaultCodec(MailMessage::class.java, MailMessageCodec(mapper))
}