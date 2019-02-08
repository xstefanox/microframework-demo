package io.github.xstefanox.demo.mf.backend

import io.github.xstefanox.demo.mf.core.Service
import mu.KLogging
import org.apache.camel.impl.DefaultCamelContext

class BackendService(
    private val backendConfiguration: BackendConfiguration,
    purchaseRouteBuilder: PurchaseRouteBuilder
) : Service {

    private val camel = DefaultCamelContext()

    init {
        camel.name = backendConfiguration.name

        camel.addRoutes(purchaseRouteBuilder)
    }

    override fun start() {
        logger.info { "starting backend service, camel name = ${backendConfiguration.name}" }
        camel.start()
    }

    override fun stop() {
        logger.info { "stopping backend service" }
        camel.stop()
    }

    companion object : KLogging()
}
