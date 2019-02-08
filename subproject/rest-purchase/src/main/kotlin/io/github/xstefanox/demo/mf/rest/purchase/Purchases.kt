package io.github.xstefanox.demo.mf.rest.purchase

import org.jetbrains.exposed.sql.Table

object Purchases : Table() {
    val pk = integer("pk").autoIncrement().primaryKey()
    val id = varchar("id", 36).uniqueIndex()
    val name = varchar("name", 50)
    val amount = integer("amount")
}
