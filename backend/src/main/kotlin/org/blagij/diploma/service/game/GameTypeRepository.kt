package org.blagij.diploma.service.game

import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import org.blagij.diploma.common.Repository

class GameTypeRepository(override val pool: PgPool) : Repository<GameType>() {
    override val mapper: Row.() -> GameType = {
        GameType(
            id = getInteger("id"),
            name = getString("name"),
            type = getString("type")
        )
    }

    suspend fun findAllByType(type: String): List<GameType> {
        return query("""
            SELECT *
            FROM game_type
            WHERE type = $1
        """.trimIndent(), Tuple.of(type))
    }

    suspend fun findGameTypeByNameIgnoreCase(name: String): GameType {
        return queryFirst("""
            SELECT *
            FROM game_type
            WHERE LOWER(name) = LOWER($1)
            LIMIT 1
        """.trimIndent(), Tuple.of(name))
    }
}

data class GameType(
    val id: Int,
    val name: String,
    val type: String
)