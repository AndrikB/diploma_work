package org.blagij.diploma.service.auth

import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.mail.MailClient
import io.vertx.ext.mail.MailConfig
import io.vertx.ext.mail.MailMessage
import io.vertx.ext.mail.StartTLSOptions
import org.blagij.diploma.common.logger


class EMailClient(val vertx: Vertx) {
    private val log = logger(this::class)

    private val mailClient by lazy {
        val config = MailConfig()
        config.hostname = "smtp.gmail.com"
        config.port = 587
        config.starttls = StartTLSOptions.REQUIRED
        config.username = "boardGameNet@gmail.com"
        config.password = "qrydztqnmukvwygh"
        MailClient.create(vertx, config)
    }

    fun send(mailMessage: MailMessage): Future<*> {
        mailMessage.from = "boardGameNet@gmail.com"
        return mailClient.sendMail(mailMessage)
            .onSuccess {
                log.info("Message sent successfully")
            }
            .onFailure{
                log.error("Could not sent message", it)
            }
    }
}