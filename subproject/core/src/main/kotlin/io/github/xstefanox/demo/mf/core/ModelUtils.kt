@file:JvmName("ModelUtils")

package io.github.xstefanox.demo.mf.core

import java.util.UUID.randomUUID

fun randomId() : Id {
    return Id(randomUUID())
}

fun String.toID(): Id = Id(this)
