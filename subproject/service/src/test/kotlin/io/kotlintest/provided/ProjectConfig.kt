package io.kotlintest.provided

import io.github.xstefanox.demo.mf.TEST_SERVICE_KODEIN
import io.github.xstefanox.demo.mf.rest.RestService
import io.kotlintest.AbstractProjectConfig
import org.kodein.di.generic.instance

@Suppress("unused") // used by kotlintest
object ProjectConfig : AbstractProjectConfig() {

    private val service by TEST_SERVICE_KODEIN.instance<RestService>()

    override fun beforeAll() {
        service.start()
    }

    override fun afterAll() {
        service.stop()
    }
}
