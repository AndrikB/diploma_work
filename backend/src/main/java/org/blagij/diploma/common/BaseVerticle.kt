package org.blagij.diploma.common

import io.vertx.kotlin.coroutines.CoroutineVerticle

abstract class BaseVerticle : CoroutineVerticle() {

    abstract val router: WebRouter

    fun routes(init: WebRouter.() -> Unit) = init(router)
}