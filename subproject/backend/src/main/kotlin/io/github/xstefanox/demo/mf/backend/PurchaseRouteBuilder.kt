package io.github.xstefanox.demo.mf.backend

import org.apache.camel.builder.RouteBuilder

class PurchaseRouteBuilder(private val rabbitMqConfiguration: RabbitMqConfiguration) : RouteBuilder() {

    override fun configure() {
        from("rabbitmq://${rabbitMqConfiguration.hostname}:${rabbitMqConfiguration.port}/purchases_ex?username=${rabbitMqConfiguration.username}&password=${rabbitMqConfiguration.password}&vhost=${rabbitMqConfiguration.vhost}&queue=purchases&autoDelete=false")
            .process { exchange ->
                println(String(exchange.`in`.body as ByteArray))
            }
    }
}