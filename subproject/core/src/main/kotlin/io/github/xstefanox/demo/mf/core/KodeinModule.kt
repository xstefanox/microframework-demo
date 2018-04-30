@file:JvmName("KodeinModule")

package io.github.xstefanox.demo.mf.core

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig
import com.hazelcast.core.HazelcastInstance
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val CORE_MODULE = Kodein.Module {

    bind<Config>() with singleton { ConfigFactory.load() }

    bind<CoreConfiguration>() with singleton { CoreConfiguration(instance()) }

    bind<HazelcastInstance>() with singleton {

        val clientConfig = ClientConfig()
        clientConfig.networkConfig.addresses = listOf("localhost:10001")
        clientConfig.setProperty("hazelcast.logging.type", "slf4j")

        HazelcastClient.newHazelcastClient(clientConfig)
    }
}
