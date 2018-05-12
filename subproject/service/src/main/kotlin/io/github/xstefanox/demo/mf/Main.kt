@file:JvmName("Main")

package io.github.xstefanox.demo.mf

import org.kodein.di.generic.instance
import java.util.concurrent.locks.ReentrantLock

fun main(args: Array<String>) {

    val service by KODEIN.instance<MainService>()

    service.use {

        val lock = ReentrantLock()
        val condition = lock.newCondition()

        lock.lock()
        condition.await()
    }
}
