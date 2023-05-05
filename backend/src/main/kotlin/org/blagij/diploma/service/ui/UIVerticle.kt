package org.blagij.diploma.service.ui

import io.vertx.ext.web.handler.StaticHandler
import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter

class UIVerticle(override val router: WebRouter) : BaseVerticle() {
    private val uiPath = arrayOf(
        "/game",
        "/chat",
        "/activate",
        "/profile",
        "/join",
        "/login",
        "/plannedGames",
    )


    init {
        routes {
            route().order(-10).handler {
                val requestPath = it.request().path()
                if (requestPath == "/" || uiPath.any { path -> requestPath.startsWith(path) }) {
                    it.response().sendFile("frontend/dist/frontend/index.html")
                } else {
                    it.next()
                }
            }.handler(
                StaticHandler.create("frontend/dist/frontend").setCachingEnabled(false)
            )
        }
    }
}
