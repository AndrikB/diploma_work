package org.blagij.diploma

import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import io.vertx.ext.web.client.WebClient
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions
import org.blagij.diploma.common.WebRouter
import org.blagij.diploma.common.single
import org.blagij.diploma.service.game.gameModule
import org.blagij.diploma.service.ui.uiModule
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


val kodein = Kodein {

    single<Vertx> { Vertx.vertx() }
    single<EventBus> { instance<Vertx>().eventBus() }

    single<WebClient> { WebClient.create(instance()) }

    single<PgPool> {
        PgPool.pool(
            instance(),
            PgConnectOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("postgres")
                .setUser("postgres")
                .setPassword("postgres"),
            PoolOptions()
                .setMaxSize(5)
        )
    }

    single<WebRouter> { WebRouter(instance()) }

    importAll(
        uiModule,
        gameModule
    )
}
