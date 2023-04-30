package org.blagij.diploma.common.codec

import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import kotlin.reflect.KClass

open class GenericJsonCodec<T : Any>(protected val jsonMapper: ObjectMapper, private val clazz: KClass<T>) :
    MessageCodec<T, T> {

    override fun decodeFromWire(position: Int, buffer: Buffer): T {
        val length = buffer.getInt(position)
        val start = position + 4
        val end = start + length

        return jsonMapper.readValue(buffer.getString(start, end), clazz.javaObjectType)
    }

    override fun encodeToWire(buffer: Buffer, message: T) {
        val content = jsonMapper.writeValueAsString(message)

        with(buffer) {
            appendInt(content.toByteArray().size)
            appendString(content)
        }
    }

    override fun transform(message: T): T = message
    override fun name(): String = clazz.simpleName!!
    override fun systemCodecID(): Byte = -1
}