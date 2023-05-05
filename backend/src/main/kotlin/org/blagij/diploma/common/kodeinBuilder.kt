package org.blagij.diploma.common

import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.TT
import org.kodein.di.bindings.NoArgSimpleBindingKodein
import org.kodein.di.bindings.Singleton
import org.kodein.di.generic
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

fun collectParams(dkodein: DKodein, construct: KFunction<Any>): Map<KParameter, Any?> {

    val className = (construct.returnType.classifier as KClass<*>).simpleName!!

    return construct.parameters.associateWith {
        val typeToken = TT(it.type.classifier as KClass<out Any>)
        if (dkodein.AllFactories(TT(String::class), typeToken).isNotEmpty()) {
            dkodein.AllFactories(TT(String::class), typeToken).first().invoke(className)
        } else {
            dkodein.Instance(typeToken)
        }
    }
}


inline fun <reified T : Any> Kodein.Builder.single(tag: Any? = null, overrides: Boolean? = null) {
    bind(tag, overrides) from singleton {
        T::class.primaryConstructor!!.callBy(collectParams(dkodein, T::class.primaryConstructor!!))
    }
}

inline fun <reified T : Any, reified K : T> Kodein.Builder.single() {
    bind<T>() with singleton {
        K::class.primaryConstructor!!.callBy(collectParams(dkodein, K::class.primaryConstructor!!))
    }
}

inline fun <reified T : Any> Kodein.Builder.single(noinline creator: NoArgSimpleBindingKodein<T>.() -> T) {
    bind<T>() with Singleton(scope, contextType, generic(), null, true, creator)
}