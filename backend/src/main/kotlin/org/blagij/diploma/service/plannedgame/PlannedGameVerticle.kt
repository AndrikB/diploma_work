package org.blagij.diploma.service.plannedgame

import io.vertx.core.json.JsonObject
import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter
import org.blagij.diploma.service.user.UserRepository
import java.util.UUID

class PlannedGameVerticle(
    override val router: WebRouter,
    private val userRepository: UserRepository,
    plannedGameRepository: PlannedGameRepository,
) : BaseVerticle() {
    init {
        routes {

            "GET /api/v1/plan" { ->
                plannedGameRepository.getAllPlanned().enrichWithUsers()
            }

            "POST /api/v1/plan" { userId: UUID, body: PlannedGame ->
                val createdGame = plannedGameRepository.createGame(body.copy(authorId = userId))
                plannedGameRepository.participate(userId, createdGame.id.toString())
                createdGame
            }

            "POST /api/v1/plan/participate" { userId: UUID, body: JsonObject ->
                plannedGameRepository.participate(userId, body.getString("plannedGameId"))
            }


        }
    }

    private suspend fun List<PlannedGame>.enrichWithUsers(): List<PlannedGame> {
        return this.map { it.copy(users = userRepository.getUserForParticipate(it.id)) }
            .map { it.copy(currentCount = it.users.count()) }

    }

}
