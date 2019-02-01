@file:JvmName("ConfigurationUtils")

package io.github.xstefanox.demo.mf.core

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

fun loadConfiguration(path: String): Config {

    require(path.isNotBlank()) {
        "path must not be empty"
    }

    val config = ConfigFactory.load(path)
    config.checkValid(ConfigFactory.defaultReference(), path)

    return config.getConfig(path)
}
