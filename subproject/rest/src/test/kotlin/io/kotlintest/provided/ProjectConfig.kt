package io.kotlintest.provided

import io.github.xstefanox.demo.mf.rest.RestService
import io.kotlintest.AbstractProjectConfig
import org.kodein.di.generic.instance
import io.github.xstefanox.demo.mf.rest.TEST_KODEIN

@Suppress("unused")   // used by kotlintest
object ProjectConfig : AbstractProjectConfig() {

    private val service by TEST_KODEIN.instance<RestService>()

    override fun beforeAll() {
        service.start()
    }

    override fun afterAll() {
        service.stop()
    }
}
