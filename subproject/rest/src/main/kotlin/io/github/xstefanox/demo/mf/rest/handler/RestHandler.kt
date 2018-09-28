package io.github.xstefanox.demo.mf.rest.handler

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.server.RoutingHandler
import io.undertow.util.Methods.GET
import io.undertow.util.Methods.POST

class RestHandler : HttpHandler {

    private val routingHandler = RoutingHandler()
    private val helloWorldHandler = HelloWorldHandler()

    init {

        routingHandler.add(GET, "/hello-world", helloWorldHandler::get)

        routingHandler.add(POST, "/echo") { exchange ->
            exchange.requestReceiver.receiveFullString { _, message: String? ->
                exchange.responseSender.send("you sent: $message")
            }
        }
    }

    override fun handleRequest(exchange: HttpServerExchange?) = routingHandler.handleRequest(exchange)
}
