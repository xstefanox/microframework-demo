package io.github.xstefanox.demo.mf.backend

import com.hazelcast.core.HazelcastInstance
import io.github.xstefanox.demo.mf.backend.route.SchedulerRouteBuilder
import mu.KLogging
import org.apache.camel.component.quartz.QuartzComponent
import org.apache.camel.main.Main
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.net.URI
import javax.cache.Cache
import javax.cache.Caching
import javax.cache.configuration.MutableConfiguration
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration


private const val TIMER_CLEANUP_CACHE_NAME = "TIMER_CLEANUP"

val BACKEND_MODULE = Kodein.Module {

    bind<BackendService>() with singleton { BackendService(instance()) }

    bind<Cache<String, String>>() with singleton {

        val cacheManager = Caching.getCachingProvider().getCacheManager(URI(instance<HazelcastInstance>().name), null)

        val configuration = MutableConfiguration<String, String>()
        configuration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE))
        configuration.isStatisticsEnabled = true

        cacheManager.destroyCache(TIMER_CLEANUP_CACHE_NAME)
        cacheManager.createCache<String, String, MutableConfiguration<String, String>>(TIMER_CLEANUP_CACHE_NAME, configuration)
    }

    bind<JCacheIdempotentRepository<String>>() with singleton { JCacheIdempotentRepository<String>(instance()) }

    bind<SchedulerRouteBuilder>() with singleton { SchedulerRouteBuilder(instance()) }
}

class BackendService(private val schedulerRouteBuilder: SchedulerRouteBuilder) {

    private val camel = Main().apply {
        orCreateCamelContext.addComponent("quartz", QuartzComponent())
    }

    operator fun invoke() {
        logger.info { "starting" }
        camel.addRouteBuilder(schedulerRouteBuilder)
        camel.run()
    }

    companion object : KLogging()
}
