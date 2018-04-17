package io.github.xstefanox.demo.mf

import io.github.xstefanox.demo.mf.backend.BackendService
import io.github.xstefanox.demo.mf.rest.RestService

class Service {

    private val backend = BackendService()
    private val restService = RestService()

    operator fun invoke() {
        restService()
        backend()
    }
}