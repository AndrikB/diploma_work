package org.blagij.diploma.service.game

import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter

class GameVerticle(
    override val router: WebRouter,
) : BaseVerticle() {
    init {
        routes {
            "GET /v1/games" {
                listOf("1234", "12131")
            }
        }
    }
}