package io.github.xstefanox.demo.mf.backend

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class BackendConfiguration(config: Config) {

    val name: String

    init {
        config.checkValid(ConfigFactory.defaultReference(), "backend")
        name = config.getString("backend.name")
    }
}