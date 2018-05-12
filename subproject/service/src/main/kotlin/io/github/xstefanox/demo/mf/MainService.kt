package io.github.xstefanox.demo.mf

import io.github.xstefanox.demo.mf.backend.BackendService
import io.github.xstefanox.demo.mf.rest.RestService
import java.io.Closeable

class MainService(
    private val restService: RestService,
    private val backend: BackendService
) : Closeable {

    init {

        Runtime.getRuntime().addShutdownHook(Thread({
            close()
        }))

        restService.start()
        backend.start()
    }

    override fun close() {
        restService.stop()
        backend.stop()
    }
}
