package org.blagij.diploma.service.game

import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter

class GameVerticle(
    override val router: WebRouter,
    httpBGGClient: HTTPGameClient,
    gameTypeRepository: GameTypeRepository,
) : BaseVerticle() {
    init {
        routes {

            "GET /api/v1/game/search/:name" { name: String ->
                httpBGGClient.searchGame(name)
            }

            "GET /api/v1/game/ids/:ids" { ids: String ->
                httpBGGClient.getByIds(ids)
            }

            "GET /api/v1/game/:id" { id: String ->
                httpBGGClient.getById(id)
            }

            "GET /api/v1/game/type/:type" { type: String ->
                gameTypeRepository.findAllByType(type)
            }

            "GET /api/v1/game/:type/:name" { name: String ->
                val gameType = gameTypeRepository.findGameTypeByNameIgnoreCase(name)

                httpBGGClient.getGamesByTypes(gameType.id)
            }
        }
    }
}