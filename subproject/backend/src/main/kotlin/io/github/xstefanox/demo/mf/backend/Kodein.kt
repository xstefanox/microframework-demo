@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.backend

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val BACKEND_MODULE = Kodein.Module {
    bind<BackendService>() with singleton { BackendService() }
}

val BACKEND_KODEIN = Kodein {
    import(BACKEND_MODULE)
}
