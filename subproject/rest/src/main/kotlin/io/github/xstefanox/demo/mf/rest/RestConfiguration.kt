package io.github.xstefanox.demo.mf.rest

import com.typesafe.config.Config

data class RestConfiguration(private val config: Config) {
    val port: Int = config.getInt("port")
    val host: String = config.getString("host")
}