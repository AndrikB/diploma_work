package org.blagij.diploma.service.auth

import io.vertx.core.Handler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.blagij.diploma.service.user.UserRepository

class CleanUpTokensPeriodic(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) : Handler<Long> {
    override fun handle(event: Long?) {
        GlobalScope.launch {
            tokenRepository.cleanUpExpiredTokens()
            userRepository.deleteNonActivatedUsers()
        }
    }
}