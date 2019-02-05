package io.github.xstefanox.demo.mf.core

import java.util.UUID

data class Id(private val value: UUID) : Comparable<Id> {

    constructor(value: String) : this(UUID.fromString(value))

    override fun compareTo(other: Id): Int {
        return value.compareTo(other.value)
    }
}
