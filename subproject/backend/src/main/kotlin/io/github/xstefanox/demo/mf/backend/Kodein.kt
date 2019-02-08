@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.backend

import io.github.xstefanox.demo.mf.core.CORE_MODULE
import io.github.xstefanox.demo.mf.core.loadConfiguration
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val BACKEND_MODULE = Kodein.Module("BACKEND") {

    bind<BackendConfiguration>() with singleton { BackendConfiguration(loadConfiguration("backend")) }

    bind<BackendService>() with singleton { BackendService(instance(), instance()) }

    bind<PurchaseRouteBuilder>() with singleton { PurchaseRouteBuilder(instance<BackendConfiguration>().rabbitmq) }
}

val BACKEND_KODEIN = Kodein {
    import(CORE_MODULE)
    import(BACKEND_MODULE)
}
