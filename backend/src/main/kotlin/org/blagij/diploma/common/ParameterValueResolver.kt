package org.blagij.diploma.common

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import org.blagij.diploma.common.ext.anyParam
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

class ParameterValueResolver(private val ctx: RoutingContext) {

    private val jsonMapper = jacksonObjectMapper()

    fun resolve(it: KParameter): Any? {
        val classifier = it.type.classifier

        return when (it.name) {
            "body" -> {
                when (classifier) {
                    JsonObject::class -> ctx.body().asJsonObject()
                    JsonArray::class -> ctx.body().asJsonArray()
                    String::class -> ctx.body().asString()
                    else -> {
                        try {
                            if (it.type.isSubtypeOf(List::class.starProjectedType)) {
                                val type = jsonMapper.typeFactory.constructCollectionType(
                                    List::class.java,
                                    (it.type.arguments.first().type!!.classifier as KClass<*>).javaObjectType
                                )

                                jsonMapper.readValue(ctx.body().asString(), type)
                            } else {
                                jsonMapper.readValue(ctx.body().asString(), (classifier as KClass<*>).java)
                            }
                        } catch (e: Exception) {
                            throw when {
                                e is MissingKotlinParameterException || e is NullPointerException || e is MismatchedInputException
                                -> IllegalArgumentException("Invalid request body format")
                                e.cause != null -> e.cause!!
                                else -> e
                            }
                        }
                    }
                }
            }
            "ctx", "context" -> ctx
            "userId", "userID" -> UUID.fromString(ctx.user().principal().getString("jti"))
            else ->
                mapValue(
                    ctx.anyParam(it.name!!),
                    it.type.classifier as KClass<*>,
                    it.type.isMarkedNullable
                )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> mapValue(value: String?, clazz: KClass<T>, isNullable: Boolean): T? {

        if (value == null) {
            return if (isNullable) null else throw IllegalArgumentException("Value of type ${clazz.simpleName} could not be null")
        }

        return if (clazz.java.isEnum) {
            clazz.java.enumConstants.first { it.toString() == value }
        } else {
            when (clazz) {
                String::class -> value as T
                Short::class -> value.toShort() as T
                Int::class -> value.toInt() as T
                Long::class -> value.toLong() as T
                UUID::class -> UUID.fromString(value) as T
                LocalDate::class -> LocalDate.parse(value, DateTimeFormatter.ISO_DATE) as T
                Boolean::class -> value.toBoolean() as T
                Double::class -> value.toDouble() as T
                else ->
                    throw UnsupportedOperationException(
                        "Can't resolve lambda input parameter with type ${clazz.qualifiedName} input value is '$value'"
                    )
            }
        }
    }
}