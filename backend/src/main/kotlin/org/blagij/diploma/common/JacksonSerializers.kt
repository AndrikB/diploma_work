package org.blagij.diploma.common

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

open class JacksonSerializers : SimpleModule() {

    init {
        serializer<OffsetDateTime> { value, generator ->
            generator.writeString(value.toString())
        }

        serializer<LocalDateTime> { value, generator ->
            generator.writeString(value.toString())
        }

        serializer<ZoneId> { value, generator ->
            generator.writeString(value.id)
        }

        serializer<LocalDate?> { value, generator ->
            if (value == null) {
                generator.writeNull()
            } else {
                generator.writeNumber(value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC))
            }
        }

        serializer<Locale> { value, generator ->
            generator.writeString(value.toLanguageTag())
        }

        deserializer<OffsetDateTime> { parser: JsonParser, _: DeserializationContext ->
            OffsetDateTime.parse(parser.valueAsString)
        }

        deserializer<LocalDateTime> { parser: JsonParser, _: DeserializationContext ->
            LocalDateTime.parse(parser.valueAsString)
        }

        deserializer<UUID> { parser: JsonParser, _: DeserializationContext ->
            UUID.fromString(parser.valueAsString)
        }

        deserializer<Locale> { parser: JsonParser, _: DeserializationContext ->
            Locale.forLanguageTag(parser.valueAsString)
        }


        keyDeserializer<LocalDate> { key: String, _: DeserializationContext ->
            LocalDate.parse(key, DateTimeFormatter.ISO_DATE)
        }
    }

    protected inline fun <reified T> serializer(noinline x: (T, JsonGenerator) -> Unit) {
        addSerializer(T::class.java, object : JsonSerializer<T>() {
            override fun serialize(value: T, gen: JsonGenerator, serializers: SerializerProvider) {
                x(value, gen)
            }
        })
    }

    protected inline fun <reified T> deserializer(noinline x: (parser: JsonParser, ctx: DeserializationContext) -> T) {
        addDeserializer(T::class.java, object : JsonDeserializer<T>() {
            override fun deserialize(parser: JsonParser, ctx: DeserializationContext): T {
                return x(parser, ctx)
            }
        })
    }

    protected inline fun <reified T> keyDeserializer(noinline x: (key: String, ctx: DeserializationContext) -> Any) {
        addKeyDeserializer(T::class.java, object : KeyDeserializer() {
            override fun deserializeKey(key: String, ctx: DeserializationContext): Any {
                return x(key, ctx)
            }
        })
    }
}
