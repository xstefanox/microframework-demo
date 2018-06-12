package io.github.xstefanox.demo.mf.rest

import io.kotlintest.specs.FeatureSpec
import io.restassured.RestAssured
import io.undertow.util.StatusCodes.METHOD_NOT_ALLOWED
import io.undertow.util.StatusCodes.NOT_FOUND
import org.kodein.di.generic.instance

class RestServiceTest : FeatureSpec({

    val restConfiguration by TEST_KODEIN.instance<RestConfiguration>()

    RestAssured.port = restConfiguration.port

    feature("the main service has been started") {

        scenario("requesting a non existing resource  should always responde with 404 NOT FOUND") {
            RestAssured.get("/this/path/does/not/exist")
                    .then()
                    .assertThat()
                    .statusCode(NOT_FOUND)
        }

        scenario("calling an unsupported verb on an existing resource, should always responde with 405 METHOD NOT ALLOWED") {
            RestAssured.post("/hello-world")
                    .then()
                    .assertThat()
                    .statusCode(METHOD_NOT_ALLOWED)
        }
    }
})
