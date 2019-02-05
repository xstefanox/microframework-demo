@file:JvmName("RandomUtils")

package io.github.xstefanox.demo.mf.core

import kotlin.random.Random

private val CHAR_POOL: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun randomString(): String {

    val length = (1..10).random()

    return (1..length).map {
        CHAR_POOL[Random.nextInt(0, CHAR_POOL.size)]
    }.joinToString("")
}
