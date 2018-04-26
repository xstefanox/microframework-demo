package io.github.xstefanox.demo.mf.core

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

data class CoreConfiguration(private val config: Config) {

    val ignite: IgniteConfiguration

    init {
        config.checkValid(ConfigFactory.defaultReference(), "core")
        ignite = IgniteConfiguration(config.getList("core.ignite.addresses").map { it.unwrapped() as String }.toSet())
    }

    data class IgniteConfiguration(val addresses: Set<String>)
}