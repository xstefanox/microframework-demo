package io.github.xstefanox.demo.mf.backend

import com.typesafe.config.Config

class BackendConfiguration(config: Config) {
    val name: String = config.getString("name")
    val rabbitmq: RabbitMqConfiguration = RabbitMqConfiguration(config.getConfig("rabbitmq"))
}
