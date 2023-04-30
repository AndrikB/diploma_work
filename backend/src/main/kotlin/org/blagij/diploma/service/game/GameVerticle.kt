package org.blagij.diploma.service.game

import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter

class GameVerticle(
    override val router: WebRouter,
    httpGameClient: HTTPGameClient,
    gameTypeRepository: GameTypeRepository,
) : BaseVerticle() {
    init {
        routes {
            "GET /api/v1/game/search/:name" { name: String ->
                httpGameClient.searchGame(name)
            }

            "GET /api/v1/game/:id" { id: String ->
                httpGameClient.getById(id)
            }

            "GET /api/v1/game/type/:type" { type: String ->
                gameTypeRepository.findAllByType(type)
            }

            "GET /api/v1/game/:type/:name" { name: String ->
                val gameType = gameTypeRepository.findGameTypeByNameIgnoreCase(name)

                httpGameClient.getGamesByTypes(gameType.id)
            }
        }
    }
}