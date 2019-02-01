@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.rest

import io.github.xstefanox.demo.mf.core.loadConfiguration
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val REST_MODULE = Kodein.Module("REST") {

    bind<RestConfiguration>() with singleton { RestConfiguration(loadConfiguration("rest")) }

    bind<RestService>() with singleton { RestService(instance()) }
}
