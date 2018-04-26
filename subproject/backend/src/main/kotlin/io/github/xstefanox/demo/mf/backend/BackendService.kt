package io.github.xstefanox.demo.mf.backend

import io.github.xstefanox.demo.mf.backend.route.SchedulerRouteBuilder
import mu.KLogging
import org.apache.camel.main.Main
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val BACKEND_MODULE = Kodein.Module {
    bind<BackendService>() with singleton { BackendService() }
}

class BackendService {

    private val camel = Main()

    operator fun invoke() {
        logger.info { "starting" }
        camel.addRouteBuilder(SchedulerRouteBuilder())
//        camel.ad
        camel.run()
    }

    companion object : KLogging()
}
