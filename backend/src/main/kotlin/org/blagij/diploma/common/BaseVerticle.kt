package org.blagij.diploma.common

import io.vertx.core.eventbus.EventBus
import io.vertx.kotlin.coroutines.CoroutineVerticle

abstract class BaseVerticle : CoroutineVerticle() {

    abstract val router: WebRouter
    val eventBus: EventBus by lazy { vertx.eventBus() }

    fun routes(init: WebRouter.() -> Unit) = init(router)
}