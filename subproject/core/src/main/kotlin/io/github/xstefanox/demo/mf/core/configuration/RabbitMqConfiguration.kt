package io.github.xstefanox.demo.mf.core.configuration

import com.typesafe.config.Config

data class RabbitMqConfiguration(
    val hostname: String,
    val port: Int,
    val username: String,
    val password: String,
    val vhost: String
) {
    constructor(config: Config) : this(
        hostname = config.getString("hostname"),
        port = config.getInt("port"),
        username = config.getString("username"),
        password = config.getString("password"),
        vhost = config.getString("vhost")
    )
}
