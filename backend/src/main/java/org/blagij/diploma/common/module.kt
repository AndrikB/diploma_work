package org.blagij.diploma.common

import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import org.blagij.diploma.service.game.gameModule
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


val kodein = Kodein {

    single<Vertx> { Vertx.vertx() }
    single<EventBus> { instance<Vertx>().eventBus() }

    single<WebRouter> {
        val router = WebRouter(instance())

        router
    }

    importAll(
        gameModule
    )
}
