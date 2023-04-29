package org.blagij.diploma.service.game

import org.blagij.diploma.common.single
import org.kodein.di.Kodein

val gameModule = Kodein.Module("game") {
    single<GameVerticle>()
    single<HTTPGameClient>()
}
