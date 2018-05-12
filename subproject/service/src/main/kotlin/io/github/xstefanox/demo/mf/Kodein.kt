@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf

import io.github.xstefanox.demo.mf.backend.BACKEND_KODEIN
import io.github.xstefanox.demo.mf.rest.REST_KODEIN
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val SERVICE_MODULE = Kodein.Module {
    bind<MainService>() with singleton { MainService(instance(), instance()) }
}

val SERVICE_KODEIN = Kodein {
    extend(BACKEND_KODEIN)
    extend(REST_KODEIN)
    import(SERVICE_MODULE)
}
