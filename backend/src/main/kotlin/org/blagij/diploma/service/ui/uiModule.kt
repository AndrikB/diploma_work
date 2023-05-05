package org.blagij.diploma.service.ui

import org.blagij.diploma.common.single
import org.kodein.di.Kodein

val uiModule = Kodein.Module("ui") {
    single<UIVerticle>()
}
