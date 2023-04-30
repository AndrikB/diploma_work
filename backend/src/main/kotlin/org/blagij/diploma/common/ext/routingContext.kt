package org.blagij.diploma.common.ext

import io.vertx.ext.web.RoutingContext

fun RoutingContext.anyParam(name: String): String? =
    pathParam(name) ?: queryParam(name).firstOrNull() ?: request().getHeader(name) ?: request().getFormAttribute(name)
