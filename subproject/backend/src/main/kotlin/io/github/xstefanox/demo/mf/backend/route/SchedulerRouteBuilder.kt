package io.github.xstefanox.demo.mf.backend.route

import io.github.xstefanox.demo.mf.backend.IgniteIdempotentRepository
import org.apache.camel.Exchange
import org.apache.camel.Exchange.TIMER_FIRED_TIME
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.processor.idempotent.MemoryIdempotentRepository
import org.apache.ignite.IgniteCache
import org.apache.ignite.Ignition
import org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import java.util.Date
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration


class SchedulerRouteBuilder : RouteBuilder() {

    private val idempotentRepository = MemoryIdempotentRepository()

    override fun configure() {

        val igniteConfiguration = IgniteConfiguration()
        igniteConfiguration.isClientMode = true

        val cacheConfiguration = CacheConfiguration<Date, Date>()
        cacheConfiguration.name = "CLEANUP"
        cacheConfiguration.atomicityMode = TRANSACTIONAL
        cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE))

        val ignite = Ignition.start(igniteConfiguration)

        val cache: IgniteCache<Date, Date> = ignite.getOrCreateCache(cacheConfiguration)
        val repository = IgniteIdempotentRepository(cache)

        from("timer:CLEANUP-1")
                .idempotentConsumer(exchangeProperty(TIMER_FIRED_TIME), repository)
                .process(this::processMessage)

        from("timer:CLEANUP-2")
                .idempotentConsumer(exchangeProperty(TIMER_FIRED_TIME), repository)
                .process(this::processMessage)
    }

    private fun processMessage(exchange: Exchange) {
        println("${Thread.currentThread().name} -> ${exchange.properties[TIMER_FIRED_TIME]} (cache size: ${idempotentRepository.cacheSize})")
    }
}