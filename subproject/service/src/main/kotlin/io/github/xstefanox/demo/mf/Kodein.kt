@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf

import io.github.xstefanox.demo.mf.backend.BACKEND_MODULE
import io.github.xstefanox.demo.mf.core.CORE_MODULE
import io.github.xstefanox.demo.mf.rest.REST_MODULE
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val SERVICE_MODULE = Kodein.Module("SERVICE") {
    bind<MainService>() with singleton { MainService(instance(), instance()) }
}

val SERVICE_KODEIN = Kodein {
    import(CORE_MODULE)
    import(REST_MODULE)
    import(BACKEND_MODULE)
    import(SERVICE_MODULE)
}
