@file:JvmName("TestKodein")

package io.github.xstefanox.demo.mf

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val TEST_SERVICE_MODULE = Kodein.Module("Test.Service", allowSilentOverride = true) {
    bind<MainService>() with provider { MainService(instance(), instance()) }
}

val TEST_SERVICE_KODEIN = Kodein {
    extend(SERVICE_KODEIN)
    import(TEST_SERVICE_MODULE, allowOverride = true)
}
