package org.blagij.diploma.common

import io.vertx.core.Future
import io.vertx.kotlin.coroutines.await
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import net.logstash.logback.argument.StructuredArguments.entries
import org.intellij.lang.annotations.Language

abstract class Repository<T> {
    abstract val pool: PgPool
    abstract val mapper: Row.() -> T

    private val sqlExceptionMessage = "SQL exception"
    protected val logger = logger(this::class)

    private fun mapper(): Row.() -> T = mapper

    suspend fun queryFirst(
        @Language("PostgreSQL") sql: String,
        params: Tuple = Tuple.tuple(),
        mapper: Row.() -> T = mapper()
    ): T {

        return Future.future<T> { queryFirst ->
            pool.getConnection().compose { conn ->
                conn
                    .preparedQuery(sql)
                    .execute(params)
                    .onComplete { rs ->
                        try {
                            if (rs.succeeded()) {
                                queryFirst.complete(rs.result().map { it.mapper() }.first())
                            } else {
                                logger.error(
                                    sqlExceptionMessage, entries(
                                        mapOf(
                                            "cause" to rs.cause(),
                                            "query" to sql.trim().replace(Regex("\\n\\s+"), " "),
                                            "params" to params
                                        )
                                    )
                                )
                                queryFirst.fail(rs.cause())
                            }
                        } catch (e: Exception) {
                            queryFirst.fail(e)
                        } finally {
                            conn.close()
                        }
                    }
            }
        }.await()
    }

    suspend fun query(
        @Language("PostgreSQL") sql: String,
        params: Tuple = Tuple.tuple(),
        mapper: Row.() -> T = mapper()
    ): List<T>  {
        return Future.future<List<T>> { queryFirst ->
            pool.getConnection().compose { conn ->
                conn
                    .preparedQuery(sql)
                    .execute(params)
                    .onComplete { rs ->
                        try {
                            if (rs.succeeded()) {
                                queryFirst.complete(rs.result().map { it.mapper() })
                            } else {
                                logger.error(
                                    sqlExceptionMessage, entries(
                                        mapOf(
                                            "cause" to rs.cause(),
                                            "query" to sql.trim().replace(Regex("\\n\\s+"), " "),
                                            "params" to params
                                        )
                                    )
                                )
                                queryFirst.fail(rs.cause())
                            }
                        } catch (e: Exception) {
                            queryFirst.fail(e)
                        } finally {
                            conn.close()
                        }
                    }
            }
        }.await()
    }

    suspend fun queryBatch(
        @Language("PostgreSQL") sql: String,
        params: List<Tuple>,
        mapper: Row.() -> T = mapper()
    ): List<T> {
        require(params.isNotEmpty()) { "Empty list for batch execution" }

        return Future.future<List<T>> { queryBatch ->
            pool.getConnection().compose { conn ->
                conn
                    .preparedQuery(sql)
                    .executeBatch(params)
                    .onComplete { rs ->
                        try {
                            if (rs.succeeded()) {
                                queryBatch.complete(rs.result().map { it.mapper() })
                            } else {
                                logger.error(
                                    sqlExceptionMessage, entries(
                                        mapOf(
                                            "cause" to rs.cause(),
                                            "query" to sql.trim().replace(Regex("\\n\\s+"), " "),
                                            "params" to params
                                        )
                                    )
                                )
                                queryBatch.fail(rs.cause())
                            }
                        } catch (e: Exception) {
                            queryBatch.fail(e)
                        } finally {
                            conn.close()
                        }

                    }
            }
        }.await()
    }
}
