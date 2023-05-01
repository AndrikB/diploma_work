package org.blagij.diploma.service.auth

import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.mail.MailMessage
import io.vertx.ext.web.handler.JWTAuthHandler
import io.vertx.kotlin.ext.auth.jwtOptionsOf
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter
import org.blagij.diploma.common.encodePassword
import org.blagij.diploma.common.serviceUrl
import java.util.UUID

class AuthVerticle(
    override val router: WebRouter,
    userRepository: UserRepository,
    tokenRepository: TokenRepository,
    private val mailClient: EMailClient,
    private val jwtAuth: JWTAuth,
    private val jwtAuthHandler: JWTAuthHandler,
) : BaseVerticle() {
    init {
        routes {

            val publicPaths = listOf(
                "/api/v1/auth",
                "/api/v1/game",
            )
            route().order(-1).handler {
                val requestPath = it.request().path()

                if (publicPaths.any { path -> requestPath.startsWith(path) }) {
                    it.next()
                } else {
                    jwtAuthHandler.handle(it)
                }
            }

            "POST /api/v1/auth/register" { body: User ->
                userRepository.createUser(body.copy(status = "UNACTIVATED", password = encodePassword(body.password)))

                val token = Token(body.id, UUID.randomUUID().toString())
                tokenRepository.storeToken(token)

                eventBus.send("EMAIL_SENDING_CHANNEL", generateMailMessage(body.mail, token.token))

                true
            }

            "POST /api/v1/auth/activate/:token" { token: String ->
                userRepository.activateUserByToken(token)
            }

            "POST /api/v1/auth" { body: AuthRequest ->
                val user = userRepository.findUser(body)

                jwtAuth.generateToken(
                    JsonObject().put("jti", user.id.toString()),
                    jwtOptionsOf(
                        algorithm = "HS512",
                        expiresInMinutes = 60,
                        subject = user.login,
                    )
                )
            }



        }
    }

    private fun generateMailMessage(mail: String, token: String): MailMessage {
        val mailMessage = MailMessage()
        mailMessage.setTo(mail)
        mailMessage.text = "$serviceUrl/activate/$token"
        return mailMessage
    }

    override suspend fun start() {
        eventBus.consumer<MailMessage>("EMAIL_SENDING_CHANNEL") { message ->
            GlobalScope.launch {
                mailClient.send(message.body())
            }
        }
    }

}

data class AuthRequest(
    val login: String,
    val password: String
)