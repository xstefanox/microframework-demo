package io.github.xstefanox.demo.mf.backend.route

import io.github.xstefanox.demo.mf.backend.JCacheIdempotentRepository
import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import java.io.File

private const val SCHEDULED_FIRE_TIME = "scheduledFireTime"

class SchedulerRouteBuilder(private val idempotentRepository: JCacheIdempotentRepository<String>) : RouteBuilder() {

    override fun configure() {

//        from("timer:CLEANUP-1")
//                .idempotentConsumer(exchangeProperty(TIMER_FIRED_TIME), idempotentRepository)
//                .process(this::processMessage)
//
//        from("timer:CLEANUP-2")
//                .idempotentConsumer(exchangeProperty(TIMER_FIRED_TIME), idempotentRepository)
//                .process(this::processMessage)

        from("quartz://CLEANUP-1?cron=* * * * * ?")
                .idempotentConsumer(header(SCHEDULED_FIRE_TIME), idempotentRepository)
                .process(this::processMessage)

        from("quartz://CLEANUP-2?cron=* * * * * ?")
                .idempotentConsumer(header(SCHEDULED_FIRE_TIME), idempotentRepository)
                .process(this::processMessage)
    }

    private fun processMessage(exchange: Exchange) {
        File("/tmp/dedup.txt").appendText("${Thread.currentThread().name} -> ${exchange.`in`.headers[SCHEDULED_FIRE_TIME]}\n")
    }
}