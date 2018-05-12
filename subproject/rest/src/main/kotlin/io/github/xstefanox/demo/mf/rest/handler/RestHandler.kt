package io.github.xstefanox.demo.mf.rest.handler

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.server.RoutingHandler
import io.undertow.util.Methods.GET

class RestHandler : HttpHandler {

    private val routingHandler = RoutingHandler()
    private val helloWorldHandler = HelloWorldHandler()

    init {
        routingHandler.add(GET, "/hello-world", helloWorldHandler::get)
    }

    override fun handleRequest(exchange: HttpServerExchange?) = routingHandler.handleRequest(exchange)
}
