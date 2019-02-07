package io.github.xstefanox.demo.mf.rest.user

import com.mongodb.async.client.MongoCollection
import com.mongodb.async.client.MongoDatabase
import io.github.xstefanox.demo.mf.core.randomId
import io.github.xstefanox.demo.mf.core.randomString
import io.github.xstefanox.demo.mf.core.toID
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.PathTemplateMatch
import io.undertow.util.StatusCodes.CREATED
import io.undertow.util.StatusCodes.NOT_FOUND
import org.litote.kmongo.async.getCollection
import org.litote.kmongo.coroutine.deleteOneById
import org.litote.kmongo.coroutine.findOneById
import org.litote.kmongo.coroutine.forEach
import org.litote.kmongo.coroutine.insertOne

class UserController(private val mongoDB: MongoDatabase) {

    private val users2: MongoCollection<User> by lazy {
        mongoDB.getCollection<User>()
    }

    suspend fun getAll(exchange: HttpServerExchange) {

        val result = mutableListOf<User>()

        users2.find().forEach { user ->
            result.add(user)
        }

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(result.sortedBy(User::id).toString())
    }

    suspend fun getOne(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("userId").toID()
        val user = users2.findOneById(id)

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (user == null) {
            exchange.statusCode = NOT_FOUND
            exchange.responseSender.send("")
        } else {
            exchange.responseSender.send(user.toString())
        }
    }

    suspend fun create(exchange: HttpServerExchange) {

        val user = User(
            randomId(),
            randomString(),
            randomString()
        )

        users2.insertOne(user)

        exchange.statusCode = CREATED
        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(user.toString())
    }

    suspend fun delete(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("userId").toID()

        val result = users2.deleteOneById(id)!!

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (result.deletedCount == 0L) {
            exchange.statusCode = NOT_FOUND
        }

        exchange.responseSender.send("")
    }
}

val HttpServerExchange.path: Map<String, String>
    get() = getAttachment(PathTemplateMatch.ATTACHMENT_KEY).parameters
