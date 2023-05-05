package org.blagij.diploma.common

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.time.DateTimeException
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ISO_INSTANT
import java.util.*

class ResponseJacksonSerializers : JacksonSerializers() {

    init {
        // Copy & pasted from io.vertx.core.json.Json
        addSerializer(JsonObject::class.java, JsonObjectSerializer())
        addSerializer(JsonArray::class.java, JsonArraySerializer())
        // he have 2 extensions: RFC-7493
        addSerializer(Instant::class.java, InstantSerializer())
        addDeserializer(Instant::class.java, InstantDeserializer())
        addSerializer(ByteArray::class.java, ByteArraySerializer())
        addDeserializer(ByteArray::class.java, ByteArrayDeserializer())
        addSerializer(JSONObject::class.java, OrgJSONObjectSerializer())
        addSerializer(JSONArray::class.java, OrgJSONArraySerializer())

        serializer<OffsetDateTime> { value, generator ->
            generator.writeNumber(value.toInstant().epochSecond)
        }
        deserializer<OffsetDateTime> { parser: JsonParser, _: DeserializationContext ->
            OffsetDateTime.ofInstant(Instant.ofEpochSecond(parser.valueAsLong), ZoneId.of("UTC"))
        }
    }

    private class JsonObjectSerializer : JsonSerializer<JsonObject>() {
        @Throws(IOException::class)
        override fun serialize(value: JsonObject, jgen: JsonGenerator, provider: SerializerProvider) {
            jgen.writeObject(value.map)
        }
    }

    private class OrgJSONObjectSerializer : JsonSerializer<JSONObject>() {
        @Throws(IOException::class)
        override fun serialize(value: JSONObject, jgen: JsonGenerator, provider: SerializerProvider) {
            jgen.writeObject(value.toMap())
        }
    }

    private class OrgJSONArraySerializer : JsonSerializer<JSONArray>() {
        @Throws(IOException::class)
        override fun serialize(value: JSONArray, jgen: JsonGenerator, provider: SerializerProvider) {
            jgen.writeObject(value.toList())
        }
    }

    private class JsonArraySerializer : JsonSerializer<JsonArray>() {
        @Throws(IOException::class)
        override fun serialize(value: JsonArray, jgen: JsonGenerator, provider: SerializerProvider) {
            jgen.writeObject(value.list)
        }
    }

    private class InstantSerializer : JsonSerializer<Instant>() {
        @Throws(IOException::class)
        override fun serialize(value: Instant, jgen: JsonGenerator, provider: SerializerProvider) {
            jgen.writeString(ISO_INSTANT.format(value))
        }
    }

    private class InstantDeserializer : JsonDeserializer<Instant>() {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Instant {
            val text = p.text
            try {
                return Instant.from(ISO_INSTANT.parse(text))
            } catch (e: DateTimeException) {
                throw InvalidFormatException(p, "Expected an ISO 8601 formatted date time", text, Instant::class.java)
            }

        }
    }

    private class ByteArraySerializer : JsonSerializer<ByteArray>() {

        @Throws(IOException::class)
        override fun serialize(value: ByteArray, jgen: JsonGenerator, provider: SerializerProvider) {
            jgen.writeString(Base64.getEncoder().encodeToString(value))
        }
    }

    private class ByteArrayDeserializer : JsonDeserializer<ByteArray>() {

        @Throws(IOException::class, JsonProcessingException::class)
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ByteArray {
            val text = p.text
            try {
                return Base64.getDecoder().decode(text)
            } catch (e: IllegalArgumentException) {
                throw InvalidFormatException(p, "Expected a base64 encoded byte array", text, Instant::class.java)
            }

        }
    }
}