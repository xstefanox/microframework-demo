package io.github.xstefanox.demo.mf.rest.purchase

import io.github.xstefanox.demo.mf.core.purchase.PurchaseManager
import io.github.xstefanox.demo.mf.core.toId
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.PathTemplateMatch
import io.undertow.util.StatusCodes.CREATED
import io.undertow.util.StatusCodes.NOT_FOUND

class PurchaseController(
    private val purchaseManager: PurchaseManager
) {

    fun getAll(exchange: HttpServerExchange) {

        val purchases = purchaseManager.getAll()

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(purchases.toString())
    }

    fun getOne(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("purchaseId").toId()

        val purchase = purchaseManager.getOne(id)

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (purchase == null) {
            exchange.statusCode = NOT_FOUND
            exchange.responseSender.send("")
        } else {
            exchange.responseSender.send(purchase.toString())
        }
    }

    fun create(exchange: HttpServerExchange) {

        val purchase = purchaseManager.create()

        exchange.statusCode = CREATED
        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(purchase.toString())
    }

    fun delete(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("purchaseId").toId()

        val found = purchaseManager.delete(id)

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (!found) {
            exchange.statusCode = NOT_FOUND
        }

        exchange.responseSender.send("")
    }
}

val HttpServerExchange.path: Map<String, String>
    get() = getAttachment(PathTemplateMatch.ATTACHMENT_KEY).parameters
