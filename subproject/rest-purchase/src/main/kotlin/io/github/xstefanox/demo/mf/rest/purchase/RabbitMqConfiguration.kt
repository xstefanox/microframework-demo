package io.github.xstefanox.demo.mf.rest.purchase

import com.typesafe.config.Config

data class RabbitMqConfiguration(private val config: Config) {
    val hostname: String = config.getString("hostname")
    val port: Int = config.getInt("port")
    val username: String = config.getString("username")
    val password: String = config.getString("password")
    val vhost: String = config.getString("vhost")
}
