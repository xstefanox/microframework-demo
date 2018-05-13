package io.github.xstefanox.demo.mf.rest

import io.github.xstefanox.demo.mf.core.Service
import io.github.xstefanox.demo.mf.rest.handler.RestHandler
import io.undertow.Handlers
import io.undertow.Undertow
import mu.KLogging

class RestService(private val restConfiguration: RestConfiguration) : Service {

    private val undertow: Undertow

    init {
        System.setProperty("org.jboss.logging.provider", "slf4j")

        undertow = Undertow.builder()
            .addHttpListener(restConfiguration.port, restConfiguration.host)
            .setHandler(RestHandler())
            .build()

        Handlers.path()
    }

    override fun start() {
        logger.info { "starting rest service, listening on ${restConfiguration.host}:${restConfiguration.port}" }
        undertow.start()
    }

    override fun stop() {
        logger.info { "stopping rest service" }
        undertow.stop()
    }

    companion object : KLogging()
}
