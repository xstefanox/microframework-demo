package io.github.xstefanox.demo.mf.rest

import io.undertow.Undertow
import io.undertow.util.Headers.CONTENT_TYPE
import mu.KLogging
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val REST_MODULE = Kodein.Module {
    bind<RestConfiguration>() with singleton { RestConfiguration(instance()) }
    bind<RestService>() with singleton { RestService(instance()) }
}

class RestService(restConfiguration: RestConfiguration) {

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

    operator fun invoke() {
        logger.info { "starting" }
        undertow.start()
    }

    companion object : KLogging()
}
