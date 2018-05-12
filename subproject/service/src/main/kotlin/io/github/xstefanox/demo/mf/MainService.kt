package io.github.xstefanox.demo.mf

import io.github.xstefanox.demo.mf.backend.BackendService
import io.github.xstefanox.demo.mf.core.Service
import io.github.xstefanox.demo.mf.rest.RestService

class MainService(
    private val restService: RestService,
    private val backend: BackendService
) : Service {

    override fun start() {
        restService.start()
        backend.start()
    }

    override fun stop() {
        restService.stop()
        backend.stop()
    }
}
