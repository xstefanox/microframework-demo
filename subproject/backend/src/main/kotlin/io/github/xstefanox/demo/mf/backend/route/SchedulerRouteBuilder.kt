package io.github.xstefanox.demo.mf.backend.route

import io.github.xstefanox.demo.mf.backend.IgniteIdempotentRepository
import org.apache.camel.Exchange
import org.apache.camel.Exchange.TIMER_FIRED_TIME
import org.apache.camel.builder.RouteBuilder
import java.util.Date


class SchedulerRouteBuilder(private val idempotentRepository: IgniteIdempotentRepository<Date>) : RouteBuilder() {

    override fun configure() {

        from("timer:CLEANUP-1")
                .idempotentConsumer(exchangeProperty(TIMER_FIRED_TIME), idempotentRepository)
                .process(this::processMessage)

        from("timer:CLEANUP-2")
                .idempotentConsumer(exchangeProperty(TIMER_FIRED_TIME), idempotentRepository)
                .process(this::processMessage)
    }

    private fun processMessage(exchange: Exchange) {
        println("${Thread.currentThread().name} -> ${exchange.properties[TIMER_FIRED_TIME]}")
    }
}