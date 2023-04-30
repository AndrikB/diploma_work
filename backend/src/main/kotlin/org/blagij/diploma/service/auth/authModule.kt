package org.blagij.diploma.service.auth

import org.blagij.diploma.common.single
import org.kodein.di.Kodein

val authModule = Kodein.Module("auth") {
    single<AuthVerticle>()
    single<UserRepository>()
    single<TokenRepository>()
    single<EMailClient>()
}
