package io.github.xstefanox.demo.mf.rest.purchase

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import io.github.xstefanox.demo.mf.core.randomId
import io.github.xstefanox.demo.mf.core.randomString
import io.github.xstefanox.demo.mf.rest.purchase.Purchase.State.NEW
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.PathTemplateMatch
import io.undertow.util.StatusCodes.CREATED
import io.undertow.util.StatusCodes.NOT_FOUND
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder.ASC
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

private val PROPERTIES = AMQP.BasicProperties.Builder()
    .contentType("text/plain")
    .build()

class PurchaseController(
    private val database: Database,
    private val connectionFactory: ConnectionFactory,
    private val objectMapper: ObjectMapper
) {

    private val connection by lazy {
        connectionFactory.newConnection()
    }

    private val channel: Channel by lazy {
        connection.createChannel()
    }

    fun getAll(exchange: HttpServerExchange) {

        val purchases = transaction(database) {
            Purchases.selectAll()
                .orderBy(Purchases.id to ASC)
                .map(ResultRow::toPurchase)
                .toList()
        }

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(purchases.toString())
    }

    fun getOne(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("purchaseId")

        val purchase = transaction(database) {
            Purchases.select { Purchases.id eq id }
                .map(ResultRow::toPurchase)
                .firstOrNull()
        }

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
            0,
            NEW
        )

        transaction(database) {
            Purchases.insert { row ->
                row[id] = purchase.id.toString()
                row[name] = purchase.name
                row[amount] = purchase.amount
                row[state] = purchase.state
            }
        }

        val message = objectMapper.writeValueAsBytes(mapOf(
            "id" to purchase.id
        ))

        channel.basicPublish("purchases_ex", "", PROPERTIES, message)

        exchange.statusCode = CREATED
        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(purchase.toString())
    }

    fun delete(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("purchaseId")

        val count = transaction(database) {
            Purchases.deleteWhere {
                Purchases.id eq id
            }
        }

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (count == 0) {
            exchange.statusCode = NOT_FOUND
        }

        exchange.responseSender.send("")
    }
}

val HttpServerExchange.path: Map<String, String>
    get() = getAttachment(PathTemplateMatch.ATTACHMENT_KEY).parameters
