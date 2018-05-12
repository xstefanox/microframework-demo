package io.github.xstefanox.demo.mf.rest

import io.github.xstefanox.demo.mf.core.Service
import io.undertow.Undertow
import io.undertow.util.Headers.CONTENT_TYPE
import mu.KLogging

class RestService(restConfiguration: RestConfiguration) : Service {

    private val undertow: Undertow

    init {
        System.setProperty("org.jboss.logging.provider", "slf4j")

        undertow = Undertow.builder()
            .addHttpListener(restConfiguration.port, restConfiguration.host)
            .setHandler({ exchange ->
                exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
                exchange.responseSender.send("Hello World")
            }).build()
    }

    override fun start() {
        logger.info { "starting rest service" }
        undertow.start()
    }

    override fun stop() {
        logger.info { "stopping rest service" }
        undertow.stop()
    }

    companion object : KLogging()
}
