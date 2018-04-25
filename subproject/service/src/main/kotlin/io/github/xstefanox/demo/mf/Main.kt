@file:JvmName("Main")

package io.github.xstefanox.demo.mf

import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.newInstance

fun main(args: Array<String>) {

    val kodein = Kodein {
        import(SERVICE_MODULE)
    }

    val service by kodein.newInstance { Service(instance(), instance()) }

    service()
}
