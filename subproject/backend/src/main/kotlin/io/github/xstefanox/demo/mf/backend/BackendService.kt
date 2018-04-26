package io.github.xstefanox.demo.mf.backend

import io.github.xstefanox.demo.mf.backend.route.SchedulerRouteBuilder
import mu.KLogging
import org.apache.camel.main.Main
import org.apache.ignite.Ignite
import org.apache.ignite.cache.CacheAtomicityMode
import org.apache.ignite.configuration.CacheConfiguration
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.util.Date
import javax.cache.Cache
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration

val BACKEND_MODULE = Kodein.Module {
    bind<BackendService>() with singleton { BackendService(instance()) }
    bind<CacheConfiguration<Date, Date>>() with singleton {
        CacheConfiguration<Date, Date>().apply {
            name = "CLEANUP"
            atomicityMode = CacheAtomicityMode.TRANSACTIONAL
            setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE))
        }
    }
    bind<Cache<Date, Date>>() with singleton { instance<Ignite>().getOrCreateCache(instance<CacheConfiguration<Date, Date>>()) }
    bind<IgniteIdempotentRepository<Date>>() with singleton { IgniteIdempotentRepository<Date>(instance()) }
    bind<SchedulerRouteBuilder>() with singleton { SchedulerRouteBuilder(instance()) }
}

class BackendService(private val schedulerRouteBuilder: SchedulerRouteBuilder) {

    private val camel = Main()

    operator fun invoke() {
        logger.info { "starting" }
        camel.addRouteBuilder(schedulerRouteBuilder)
        camel.run()
    }

    companion object : KLogging()
}
