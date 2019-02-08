@file:JvmName("Main")

package io.github.xstefanox.demo.mf.backend

import org.kodein.di.generic.instance
import java.util.concurrent.locks.ReentrantLock

fun main() {

    System.setProperty("org.jboss.logging.provider", "slf4j")

    val backend by BACKEND_KODEIN.instance<BackendService>()

    val lock = ReentrantLock()
    val condition = lock.newCondition()

    Runtime.getRuntime().addShutdownHook(Thread {
        backend.stop()
    })

    backend.start()

    lock.lock()
    condition.await()

    backend.stop()
}
