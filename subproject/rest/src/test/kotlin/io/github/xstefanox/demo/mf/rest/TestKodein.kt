@file:JvmName("TestKodein")

package io.github.xstefanox.demo.mf.rest

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val TEST_SERVICE_MODULE = Kodein.Module(allowSilentOverride = true) {
    bind<RestService>() with provider { RestService(instance()) }
}

val TEST_KODEIN = Kodein {
    import(REST_MODULE)
    import(TEST_SERVICE_MODULE, allowOverride = true)
}
