@file:JvmName("KodeinModule")

package io.github.xstefanox.demo.mf.core

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.logger.slf4j.Slf4jLogger
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val CORE_MODULE = Kodein.Module {
    bind<Config>() with singleton { ConfigFactory.load() }
    bind<CoreConfiguration>() with singleton { CoreConfiguration(instance()) }
    bind<Ignite>() with singleton {

        val coreConfiguration = instance<CoreConfiguration>()

        val tcpDiscoveryVmIpFinder = TcpDiscoveryVmIpFinder()
        tcpDiscoveryVmIpFinder.setAddresses(coreConfiguration.ignite.addresses)

        val tcpDiscoverySpi = TcpDiscoverySpi()
        tcpDiscoverySpi.ipFinder = tcpDiscoveryVmIpFinder

        val slf4jLogger = Slf4jLogger()

        val igniteConfiguration = IgniteConfiguration()
        igniteConfiguration.isClientMode = true
        igniteConfiguration.discoverySpi = tcpDiscoverySpi
        igniteConfiguration.gridLogger = slf4jLogger

        Ignition.start(igniteConfiguration)
    }
}
