package org.blagij.diploma.service.plannedgame

import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import org.blagij.diploma.common.Repository
import org.blagij.diploma.service.user.User
import java.sql.Date
import java.util.UUID

class PlannedGameRepository(override val pgPool: PgPool) : Repository<PlannedGame>() {

    override val mapper: Row.() -> PlannedGame = {
        PlannedGame(
            id = getUUID("planned_game_id"),
            gameId = getInteger("game_id"),
            authorId = getUUID("author_id"),
            countPlayers = getInteger("max_count_players"),
            date = Date.valueOf(getLocalDate("date")),
            address = getString("address"),
        )
    }

    suspend fun participate(userId: UUID, gameId: String) {
        exec(
            """
            INSERT INTO game_participants(user_id, planned_game_id) 
            VALUES ($1, $2)
        """.trimIndent(), Tuple.of(userId, gameId)
        )
    }

    suspend fun getAllPlanned(): List<PlannedGame> {
        return query(
            """
            SELECT * 
            FROM planned_games
        """.trimIndent()
        )
    }

    suspend fun createGame(plannedGame: PlannedGame): PlannedGame {
        return queryFirst(
            """
            INSERT INTO planned_games(planned_game_id, game_id, author_id, max_count_players, date, address) 
            VALUES ($1, $2, $3, $4, $5, $6)
            RETURNING *
        """.trimIndent(),
            Tuple.of(
                plannedGame.id,
                plannedGame.gameId,
                plannedGame.authorId,
                plannedGame.countPlayers,
                plannedGame.date.toLocalDate(),
                plannedGame.address
            )
        )
    }

}

data class PlannedGame(
    val id: UUID = UUID.randomUUID(),
    val gameId: Int,
    val authorId: UUID?,
    val countPlayers: Int,
    val currentCount: Int? = null,
    val users: List<User> = emptyList(),
    val date: Date,
    val address: String
)
