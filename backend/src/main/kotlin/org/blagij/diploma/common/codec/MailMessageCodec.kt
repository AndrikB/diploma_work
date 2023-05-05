package org.blagij.diploma.common.codec

import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.ext.mail.MailMessage

class MailMessageCodec(jsonMapper: ObjectMapper) : GenericJsonCodec<MailMessage>(jsonMapper, MailMessage::class)