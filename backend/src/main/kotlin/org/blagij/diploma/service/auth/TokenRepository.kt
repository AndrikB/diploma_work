package org.blagij.diploma.service.auth

import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import org.blagij.diploma.common.Repository
import java.util.UUID

class TokenRepository(override val pgPool: PgPool) : Repository<Token>() {
    override val mapper: Row.() -> Token = {
        Token(
            userId = getUUID("user_id"),
            token = getString("token"),
        )
    }

    suspend fun storeToken(token: Token) : Token {
        return queryFirst("""
            INSERT INTO tokens(user_id, token) 
            VALUES ($1, $2)
            returning *
        """.trimIndent(), Tuple.of(token.userId, token.token))
    }

}

data class Token(
    val userId: UUID,
    val token: String
)