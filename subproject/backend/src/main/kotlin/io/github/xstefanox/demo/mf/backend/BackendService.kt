package io.github.xstefanox.demo.mf.backend

import io.github.xstefanox.demo.mf.core.Service
import mu.KLogging
import org.apache.camel.impl.DefaultCamelContext

class BackendService : Service {

    private val camel = DefaultCamelContext()

    override fun start() {
        logger.info { "starting backend service" }
        camel.start()
    }

    override fun stop() {
        logger.info { "stopping backend service" }
        camel.stop()
    }

    companion object : KLogging()
}
