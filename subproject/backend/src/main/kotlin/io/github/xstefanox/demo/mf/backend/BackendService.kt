package io.github.xstefanox.demo.mf.backend

import mu.KLogging
import org.apache.camel.main.Main

class BackendService {

    private val camel = Main()

    operator fun invoke() {
        logger.info { "starting" }
        camel.run()
    }

    companion object : KLogging()
}
