package io.github.xstefanox.demo.mf.rest

import io.undertow.Undertow
import io.undertow.util.Headers.CONTENT_TYPE
import mu.KLogging

class RestService {

    private val undertow = Undertow.builder()
            .addHttpListener(8080, "localhost")
            .setHandler({ exchange ->
                exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
                exchange.responseSender.send("Hello World")
            }).build()

    init {
        System.setProperty("org.jboss.logging.provider", "slf4j")
    }

    operator fun invoke() {
        logger.info { "starting" }
        undertow.start()
    }

    companion object : KLogging()
}
