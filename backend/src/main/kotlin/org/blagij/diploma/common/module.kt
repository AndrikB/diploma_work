package org.blagij.diploma.common

import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import io.vertx.ext.web.client.WebClient
import org.blagij.diploma.service.game.gameModule
import org.blagij.diploma.service.ui.uiModule
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


val kodein = Kodein {

    single<Vertx> { Vertx.vertx() }
    single<EventBus> { instance<Vertx>().eventBus() }

    single<WebClient> { WebClient.create(instance()) }

    single<WebRouter> {
        val router = WebRouter(instance())

        router
    }

    importAll(
        uiModule,
        gameModule
    )
}
