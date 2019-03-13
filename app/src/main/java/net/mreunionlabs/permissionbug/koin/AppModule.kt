package net.mreunionlabs.permissionbug.koin

import net.mreunionlabs.permissionbug.ui.vm.BwMapFragmentViewModel
import net.mreunionlabs.permissionbug.util.CurrentLocationListenerKt
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    // single instance of the repository
    single { CurrentLocationListenerKt(get()) }

    viewModel { BwMapFragmentViewModel(get()) }
}