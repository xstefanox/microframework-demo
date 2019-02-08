@file:JvmName("Utils")

package io.github.xstefanox.demo.mf.rest.purchase

import io.github.xstefanox.demo.mf.core.Id
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toPurchase() : Purchase {
    return Purchase(
        Id(this[Purchases.id]),
        this[Purchases.name],
        this[Purchases.amount],
        this[Purchases.state]
    )
}
