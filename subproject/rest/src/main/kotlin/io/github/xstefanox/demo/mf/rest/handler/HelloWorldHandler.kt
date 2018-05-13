package io.github.xstefanox.demo.mf.rest.handler

import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers.CONTENT_TYPE

class HelloWorldHandler {

    fun get(exchange: HttpServerExchange) {
        exchange.responseHeaders.put(CONTENT_TYPE, "text/plain")
        exchange.responseSender.send("Hello World")
    }
}