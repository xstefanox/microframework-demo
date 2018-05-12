package io.github.xstefanox.demo.mf

import assertk.assert
import assertk.assertions.isInstanceOf
import io.restassured.RestAssured
import org.apache.http.HttpStatus.SC_OK
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.kodein.di.generic.instance
import java.net.ConnectException

object MainServiceTest : Spek({

    given("the main service has been started") {

        val service by TEST_KODEIN.instance<MainService>()

        on("requesting the base uri") {
            it("should respond with success") {
                service.use {
                    RestAssured.get("/")
                        .then()
                        .assertThat()
                        .statusCode(SC_OK)
                }
            }
        }
    }

    given("the main service has been stopped") {

        val service by TEST_KODEIN.instance<MainService>()
        service.close()

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