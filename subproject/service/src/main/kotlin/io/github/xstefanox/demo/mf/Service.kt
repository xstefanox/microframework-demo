package io.github.xstefanox.demo.mf

import io.github.xstefanox.demo.mf.backend.BACKEND_MODULE
import io.github.xstefanox.demo.mf.backend.BackendService
import io.github.xstefanox.demo.mf.core.CORE_MODULE
import io.github.xstefanox.demo.mf.rest.REST_MODULE
import io.github.xstefanox.demo.mf.rest.RestService
import org.kodein.di.Kodein

val SERVICE_MODULE = Kodein.Module {
    import(CORE_MODULE)
    import(REST_MODULE)
    import(BACKEND_MODULE)
}

class Service(
        private val restService: RestService,
        private val backend: BackendService
) {

    operator fun invoke() {
        restService()
        backend()
    }
}