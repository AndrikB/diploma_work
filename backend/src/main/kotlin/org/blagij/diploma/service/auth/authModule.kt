package org.blagij.diploma.service.auth

import io.vertx.ext.auth.PubSecKeyOptions
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.auth.jwt.JWTAuthOptions
import org.blagij.diploma.common.single
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

val authModule = Kodein.Module("auth") {
    single<AuthVerticle>()
    single<UserRepository>()
    single<TokenRepository>()
    single<EMailClient>()
    single<JWTAuth> {
        JWTAuth.create(
            instance(),
            JWTAuthOptions()
                .addPubSecKey(
                    PubSecKeyOptions()
                        .setAlgorithm("HS512")
                        .setBuffer("442fb26137ff7c815c618bfeb759787f512b296b")
                )
        )
    }
}
