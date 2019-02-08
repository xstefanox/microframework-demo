@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.rest.purchase

import io.github.xstefanox.demo.mf.core.CORE_MODULE
import io.github.xstefanox.demo.mf.core.loadConfiguration
import io.github.xstefanox.underkow.undertow
import io.undertow.Undertow
import io.undertow.server.HttpServerExchange
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val REST_MODULE = Kodein.Module("REST") {

    bind<RestConfiguration>() with singleton { RestConfiguration(loadConfiguration("rest")) }

    bind<PurchaseController>() with singleton { PurchaseController(instance()) }

    bind<Undertow>() with singleton {

        val restConfiguration = instance<RestConfiguration>()
        val purchaseController = instance<PurchaseController>()

        undertow {
            port = restConfiguration.port
            host = restConfiguration.host

            routing {
                get("/purchases", suspending(purchaseController::getAll))
                get("/purchases/{purchaseId}", suspending(purchaseController::getOne))
                post("/purchases", suspending(purchaseController::create))
                delete("/purchases/{purchaseId}", suspending(purchaseController::delete))
            }
        }
    }

    bind<Database>() with singleton {

        val database = Database.connect(instance<RestConfiguration>().db.toString(), driver = "com.mysql.cj.jdbc.Driver")

        transaction(database) {
            SchemaUtils.create(Purchases)
        }

        database
    }
}

val REST_KODEIN = Kodein {
    import(CORE_MODULE)
    import(REST_MODULE)
}

private fun suspending(block: (HttpServerExchange) -> Unit): suspend (HttpServerExchange) -> Unit = { httpServerExchange ->
    block(httpServerExchange)
}
