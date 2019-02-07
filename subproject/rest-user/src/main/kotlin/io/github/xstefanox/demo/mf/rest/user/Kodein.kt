@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.rest.user

import com.mongodb.async.client.MongoDatabase
import io.github.xstefanox.demo.mf.core.CORE_MODULE
import io.github.xstefanox.demo.mf.core.loadConfiguration
import io.github.xstefanox.underkow.undertow
import io.undertow.Undertow
import io.undertow.server.HttpServerExchange
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.litote.kmongo.async.KMongo

val REST_MODULE = Kodein.Module("REST") {

    bind<RestConfiguration>() with singleton { RestConfiguration(loadConfiguration("rest")) }

    bind<UserController>() with singleton { UserController(instance()) }

    bind<Undertow>() with singleton {

        val restConfiguration = instance<RestConfiguration>()
        val userController = instance<UserController>()

        undertow {
            port = restConfiguration.port
            host = restConfiguration.host

            routing {
                get("/users", userController::getAll)
                get("/users/{userId}", userController::getOne)
                post("/users", userController::create)
                delete("/users/{userId}", userController::delete)
            }
        }
    }

    bind<MongoDatabase>() with singleton {
        KMongo.createClient(instance<RestConfiguration>().db.toString()).getDatabase("userdb")
    }
}

val REST_KODEIN = Kodein {
    import(CORE_MODULE)
    import(REST_MODULE)
}

private fun suspending(block: (HttpServerExchange) -> Unit): suspend (HttpServerExchange) -> Unit = { httpServerExchange ->
    block(httpServerExchange)
}
