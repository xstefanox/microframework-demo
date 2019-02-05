package io.github.xstefanox.demo.mf.rest.user

import io.github.xstefanox.demo.mf.core.Id
import io.github.xstefanox.demo.mf.core.randomId
import io.github.xstefanox.demo.mf.core.randomString
import io.github.xstefanox.demo.mf.core.toID
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.PathTemplateMatch
import io.undertow.util.StatusCodes.CREATED
import io.undertow.util.StatusCodes.NOT_FOUND

class UserController {

    private val users = mutableMapOf<Id, User>()

    fun getAll(exchange: HttpServerExchange) {

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(users.values.sortedBy(User::id).toString())
    }

    fun getOne(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("userId").toID()
        val user = users[id]

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (user == null) {
            exchange.statusCode = NOT_FOUND
            exchange.responseSender.send("")
        } else {
            exchange.responseSender.send(user.toString())
        }
    }

    fun create(exchange: HttpServerExchange) {

        val user = User(
            randomId(),
            randomString(),
            randomString()
        )

        users[user.id] = user

        exchange.statusCode = CREATED
        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send(user.toString())
    }

    fun delete(exchange: HttpServerExchange) {

        val id = exchange.path.getValue("userId").toID()
        val user = users.remove(id)

        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")

        if (user == null) {
            exchange.statusCode = NOT_FOUND
            exchange.responseSender.send("")
        } else {
            exchange.responseSender.send(user.toString())
        }
    }
}

val HttpServerExchange.path: Map<String, String>
    get() = getAttachment(PathTemplateMatch.ATTACHMENT_KEY).parameters



