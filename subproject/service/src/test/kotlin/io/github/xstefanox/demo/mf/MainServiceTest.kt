package io.github.xstefanox.demo.mf

import assertk.assert
import assertk.assertions.isInstanceOf
import io.github.xstefanox.demo.mf.rest.RestConfiguration
import io.restassured.RestAssured
import org.apache.http.HttpStatus.SC_OK
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.kodein.di.generic.instance
import java.net.ConnectException

object MainServiceTest : Spek({

    val service by TEST_SERVICE_KODEIN.instance<MainService>()
    val restConfiguration by TEST_SERVICE_KODEIN.instance<RestConfiguration>()

    RestAssured.port = restConfiguration.port
    RestAssured.baseURI = "http://localhost"

    given("the main service has been started") {

        beforeGroup {
            service.start()
        }

        afterGroup {
            service.stop()
        }

        on("requesting the base uri") {
            it("should respond with success") {
                RestAssured.get("/hello-world")
                    .then()
                    .assertThat()
                    .statusCode(SC_OK)
            }
        }
    }

    given("the main service has been stopped") {

        service.start()
        service.stop()

        on("requesting the base uri") {
            it("should not connect") {
                assert {
                    RestAssured.get("/")
                }.thrownError {
                    isInstanceOf(ConnectException::class.java)
                }
            }
        }
    }
})