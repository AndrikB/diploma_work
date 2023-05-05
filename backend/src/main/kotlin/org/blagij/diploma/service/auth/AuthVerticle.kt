package org.blagij.diploma.service.auth

import io.vertx.ext.mail.MailMessage
import kotlinx.coroutines.*
import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter
import java.util.UUID

class AuthVerticle(
    override val router: WebRouter,
    userRepository: UserRepository,
    tokenRepository: TokenRepository,
    private val mailClient: EMailClient,
) : BaseVerticle() {
    init {
        routes {

            "POST /api/v1/auth/register" { body: User ->
                userRepository.createUser(body.copy(status = "UNACTIVATED")) // todo encode password

                val token = Token(body.id, UUID.randomUUID().toString())
                tokenRepository.storeToken(token)

                eventBus.send("EMAIL_SENDING_CHANNEL", generateMailMessage(body.mail, token.token))
                //send token
                true
            }

            "POST /api/v1/auth/activate/:token" { token: String ->
                userRepository.activateUserByToken(token)
            }
        }
    }

    private fun generateMailMessage(mail: String, token: String): MailMessage {
        val mailMessage = MailMessage()
        mailMessage.setTo(mail)
        mailMessage.text = "http://localhost:8080/activate/$token"
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