@file:JvmName("Main")

package io.github.xstefanox.demo.mf.rest.purchase

import io.undertow.Undertow
import org.kodein.di.generic.instance
import java.util.concurrent.locks.ReentrantLock

fun main() {

    System.setProperty("org.jboss.logging.provider", "slf4j")

    val undertow by REST_KODEIN.instance<Undertow>()

    undertow.run()
}

fun Undertow.run() {

    val lock = ReentrantLock()
    val condition = lock.newCondition()

    Runtime.getRuntime().addShutdownHook(Thread {
        stop()
    })

    start()

    lock.lock()
    condition.await()

    stop()
}
