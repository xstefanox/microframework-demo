package io.github.xstefanox.demo.mf.backend

import org.apache.camel.spi.IdempotentRepository
import javax.cache.Cache

class IgniteIdempotentRepository<T>(private val cache: Cache<T, T>) : IdempotentRepository<T> {

    override fun contains(key: T): Boolean {
        return cache.containsKey(key)
    }

    override fun add(key: T): Boolean {
        return !cache.putIfAbsent(key, key)
    }

    override fun clear() {
        cache.removeAll()
    }

    override fun start() {
    }

    override fun stop() {
    }

    override fun remove(key: T): Boolean {
        return cache.remove(key)
    }

    override fun confirm(key: T): Boolean = true
}