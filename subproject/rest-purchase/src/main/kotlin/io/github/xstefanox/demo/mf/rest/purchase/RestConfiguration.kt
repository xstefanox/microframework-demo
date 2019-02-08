package io.github.xstefanox.demo.mf.rest.purchase

import com.typesafe.config.Config
import java.net.URI

data class RestConfiguration(private val config: Config) {
    val port: Int = config.getInt("port")
    val host: String = config.getString("host")
    val db: URI = URI(config.getString("db"))
}