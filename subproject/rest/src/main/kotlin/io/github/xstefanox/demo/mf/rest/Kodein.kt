@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.rest

import com.typesafe.config.ConfigFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val REST_MODULE = Kodein.Module("REST") {

    bind<RestConfiguration>() with singleton {
        val path = "rest"
        val config = ConfigFactory.load(path)
        config.checkValid(ConfigFactory.defaultReference(), path)
        RestConfiguration(config.getConfig(path))
    }

    bind<RestService>() with singleton { RestService(instance()) }
}
