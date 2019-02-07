package io.github.xstefanox.demo.mf.rest.purchase

import io.github.xstefanox.demo.mf.core.Id
import io.github.xstefanox.demo.mf.core.randomId
import io.github.xstefanox.demo.mf.core.randomString
import io.github.xstefanox.demo.mf.core.toID
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.PathTemplateMatch
import io.undertow.util.StatusCodes.CREATED
import io.undertow.util.StatusCodes.NOT_FOUND

class PurchaseController {

    private val purchases = mutableMapOf<Id, Purchase>()

    fun getAll(exchange: HttpServerExchange) {

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(purchases.values.sortedBy(Purchase::id).toString())
    }

    fun getOne(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("purchaseId").toID()
        val purchase = purchases[id]

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (purchase == null) {
            exchange.statusCode = NOT_FOUND
            exchange.responseSender.send("")
        } else {
            exchange.responseSender.send(purchase.toString())
        }
    }

    fun create(exchange: HttpServerExchange) {

        val purchase = Purchase(
            randomId(),
            randomString(),
            0
        )

        purchases[purchase.id] = purchase

        exchange.statusCode = CREATED
        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(purchase.toString())
    }

    fun delete(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("purchaseId").toID()
        val purchase = purchases.remove(id)

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (purchase == null) {
            exchange.statusCode = NOT_FOUND
            exchange.responseSender.send("")
        } else {
            exchange.responseSender.send(purchase.toString())
        }
    }
}

val HttpServerExchange.path: Map<String, String>
    get() = getAttachment(PathTemplateMatch.ATTACHMENT_KEY).parameters
