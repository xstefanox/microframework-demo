@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.rest

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val REST_MODULE = Kodein.Module {
    bind<Config>() with singleton { ConfigFactory.load() }
    bind<RestConfiguration>() with singleton { RestConfiguration(instance()) }
    bind<RestService>() with singleton { RestService(instance()) }
}

val REST_KODEIN = Kodein {
    import(REST_MODULE)
}
