@file:JvmName("Main")

package io.github.xstefanox.demo.mf

import org.kodein.di.generic.instance
import java.util.concurrent.locks.ReentrantLock

fun main(args: Array<String>) {

    val service by SERVICE_KODEIN.instance<MainService>()

    val lock = ReentrantLock()
    val condition = lock.newCondition()

    Runtime.getRuntime().addShutdownHook(Thread {
        service.stop()
    })

    service.start()

    lock.lock()
    condition.await()

    service.stop()
}
