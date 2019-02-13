package io.github.xstefanox.demo.mf.backend

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.xstefanox.demo.mf.core.configuration.RabbitMqConfiguration
import io.github.xstefanox.demo.mf.core.purchase.PurchaseMessage
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.jackson.JacksonDataFormat

class PurchaseRouteBuilder(
    private val rabbitMqConfiguration: RabbitMqConfiguration,
    private val purchaseProcessor: PurchaseProcessor,
    private val objectMapper: ObjectMapper
) : RouteBuilder() {

    override fun configure() {

        val dataFormat = JacksonDataFormat(objectMapper, PurchaseMessage::class.java)

        from("rabbitmq://${rabbitMqConfiguration.hostname}:${rabbitMqConfiguration.port}/purchases_ex?username=${rabbitMqConfiguration.username}&password=${rabbitMqConfiguration.password}&vhost=${rabbitMqConfiguration.vhost}&queue=purchases&autoDelete=false")
            .unmarshal(dataFormat)
            .bean(purchaseProcessor, "complete")

    }
}