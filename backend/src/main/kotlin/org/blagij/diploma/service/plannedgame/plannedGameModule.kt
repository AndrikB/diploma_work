package org.blagij.diploma.service.plannedgame

import org.blagij.diploma.common.single
import org.kodein.di.Kodein

val plannedGameModule = Kodein.Module("plannedGame") {
    single<PlannedGameVerticle>()
    single<PlannedGameRepository>()
}
