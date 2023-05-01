package org.blagij.diploma.service.user

import org.blagij.diploma.common.single
import org.kodein.di.Kodein

val userModule = Kodein.Module("user") {
    single<UserVerticle>()
    single<UserRepository>()
}
