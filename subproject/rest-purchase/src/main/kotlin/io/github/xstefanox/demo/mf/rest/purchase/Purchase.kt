package io.github.xstefanox.demo.mf.rest.purchase

import io.github.xstefanox.demo.mf.core.Id

data class Purchase(
    val id: Id,
    val name: String,
    val amount: Int
)
