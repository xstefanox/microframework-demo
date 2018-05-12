package io.github.xstefanox.demo.mf.rest

import io.restassured.RestAssured
import io.undertow.util.StatusCodes.METHOD_NOT_ALLOWED
import io.undertow.util.StatusCodes.NOT_FOUND
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.kodein.di.generic.instance

object RestServiceTest : Spek({

    val service by TEST_KODEIN.instance<RestService>()

    given("the main service has been started") {

        beforeGroup {
            service.start()
        }

        afterGroup {
            service.stop()
        }

        on("requesting a non existing resource") {
            it("should always responde with 404 NOT FOUND") {
                RestAssured.get("/this/path/does/not/exist")
                    .then()
                    .assertThat()
                    .statusCode(NOT_FOUND)
            }
        }

        on("calling an unsupported verb on an existing resource") {
            it("should always responde with 405 METHOD NOT ALLOWED") {
                RestAssured.post("/hello-world")
                    .then()
                    .assertThat()
                    .statusCode(METHOD_NOT_ALLOWED)
            }
        }
    }
})
