@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.backend

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val BACKEND_MODULE = Kodein.Module("BACKEND") {
    bind<BackendConfiguration>() with singleton { BackendConfiguration(instance()) }
    bind<BackendService>() with singleton { BackendService(instance()) }
}
