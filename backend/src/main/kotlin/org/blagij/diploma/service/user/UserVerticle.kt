package org.blagij.diploma.service.user

import org.blagij.diploma.common.BaseVerticle
import org.blagij.diploma.common.WebRouter
import org.blagij.diploma.service.auth.UserRepository
import java.util.UUID

class UserVerticle(
    override val router: WebRouter,
    userRepository: UserRepository,
) : BaseVerticle() {
    init {
        routes {

            "GET /api/v1/user/me" { userId: UUID ->
                userRepository.getUserByUserId(userId)
            }

        }
    }

}
