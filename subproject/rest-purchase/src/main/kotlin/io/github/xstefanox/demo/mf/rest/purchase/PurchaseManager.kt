package io.github.xstefanox.demo.mf.rest.purchase

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import io.github.xstefanox.demo.mf.core.Id
import io.github.xstefanox.demo.mf.core.randomId
import io.github.xstefanox.demo.mf.core.randomString
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder.ASC
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

private val PROPERTIES = AMQP.BasicProperties.Builder()
    .contentType("text/plain")
    .build()

class PurchaseManager(
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

    fun getAll() = transaction(database) {
        Purchases.selectAll()
            .orderBy(Purchases.id to ASC)
            .map(ResultRow::toPurchase)
            .toList()
    }

    fun getOne(id: Id) = transaction(database) {
        Purchases.select { Purchases.id eq id.toString() }
            .map(ResultRow::toPurchase)
            .firstOrNull()
    }

    fun create(): Purchase {

        val purchase = Purchase(
            randomId(),
            randomString(),
            0,
            Purchase.State.NEW
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

        return purchase
    }

    fun delete(id: Id): Boolean {

        val count = transaction(database) {
            Purchases.deleteWhere {
                Purchases.id eq id.toString()
            }
        }

        return count > 0
    }

    fun setState(id: Id, state: Purchase.State) {
        transaction(database) {
            Purchases.update {
                Purchases.id eq id.toString()
            }
        }
    }
}
