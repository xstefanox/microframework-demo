@file:JvmName("KodeinModule")

package io.github.xstefanox.demo.mf.core

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val CORE_MODULE = Kodein.Module {
    bind<Config>() with singleton { ConfigFactory.load() }
    bind<Ignite>() with singleton {

        val igniteConfiguration = IgniteConfiguration()
        igniteConfiguration.isClientMode = true

        Ignition.start(igniteConfiguration)
    }
}
