package io.github.xstefanox.demo.mf

//import assertk.assert
//import assertk.assertions.isInstanceOf
import io.github.xstefanox.demo.mf.rest.RestConfiguration
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FeatureSpec
import io.restassured.RestAssured
import org.apache.http.HttpStatus.SC_OK
import org.kodein.di.generic.instance
import java.net.ConnectException

class MainServiceTest : FeatureSpec({

    val service by TEST_SERVICE_KODEIN.instance<MainService>()
    val restConfiguration by TEST_SERVICE_KODEIN.instance<RestConfiguration>()

    RestAssured.port = restConfiguration.port
    RestAssured.baseURI = "http://localhost"

    feature("service startup and shutdown") {

        scenario("is the service is started, it should respond") {

            RestAssured.get("/hello-world")
                    .then()
                    .assertThat()
                    .statusCode(SC_OK)
        }

        scenario("is the service has been stopped, it should not respond") {

            service.stop()

            shouldThrow<ConnectException> {
                RestAssured.get("/")
            }
        }
    }
})