package io.github.xstefanox.demo.mf.rest

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

data class RestConfiguration(private val config: Config) {

    val port: Int
    val host: String

    init {
        config.checkValid(ConfigFactory.defaultReference(), "rest")
        port = config.getInt("rest.port")
        host = config.getString("rest.host")
    }
}