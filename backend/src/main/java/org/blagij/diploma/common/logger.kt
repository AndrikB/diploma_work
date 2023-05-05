package org.blagij.diploma.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun logger(clazz: KClass<*>): Logger = logger(clazz.simpleName!!)
fun logger(name: String): Logger = LoggerFactory.getLogger(name)