package org.blagij.diploma.service.user

import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import org.blagij.diploma.common.Repository
import org.blagij.diploma.service.auth.AuthRequest
import java.util.UUID

class UserRepository(override val pgPool: PgPool) : Repository<User>() {
    override val mapper: Row.() -> User = {
        User(
            id = getUUID("user_id"),
            login = getString("login"),
            mail = getString("mail"),
            status = getString("status"),
            password = getString("password"),
            firstName = getString("firstname"),
            secondName = getString("lastname"),
            profile = getString("profile"),
        )
    }

    suspend fun createUser(user: User): User {
        return queryFirst("""
            INSERT INTO users(user_id, login, mail, status, password, firstname, lastname, profile) 
            VALUES ($1, $2, $3, $4, $5, $6, $7, $8)
            returning *
        """.trimIndent(), Tuple.of(user.id, user.login, user.mail, user.status, user.password, user.firstName, user.secondName, user.profile))
    }

    suspend fun activateUserByToken(token: String): User {
        return queryFirst("""
            UPDATE users
            SET status='ACTIVATED'
            WHERE user_id = (SELECT user_id from tokens where token = $1)
            returning *
        """.trimIndent(), Tuple.of(token)
        )
    }

    suspend fun findUser(body: AuthRequest): User {
        return queryFirst("""
            SELECT *
            FROM users
            WHERE login=$1 AND password=$2
        """.trimIndent(), Tuple.of(body.login, body.password)
        )

    }

    suspend fun getUserByUserId(userId: UUID): User {
        return queryFirst("""
            SELECT *
            FROM users
            WHERE user_id=$1
        """.trimIndent(), Tuple.of(userId)
        )
    }

    suspend fun searchUserByLogin(login: String): List<User> {
        return query("""
            SELECT *
            FROM users
            WHERE login ILIKE $1
        """.trimIndent(), Tuple.of(login)
        )

    }

}
data class User(
    val id: UUID = UUID.randomUUID(),
    val login: String,
    val mail: String,
    val status: String = "UNACTIVATED",
    val password: String,
    val firstName: String?,
    val secondName: String?,
    val profile: String?
)