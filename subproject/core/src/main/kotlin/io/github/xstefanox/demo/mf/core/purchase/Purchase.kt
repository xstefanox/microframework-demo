package io.github.xstefanox.demo.mf.core.purchase

import io.github.xstefanox.demo.mf.core.Id

data class Purchase(
    val id: Id,
    val name: String,
    val amount: Int,
    val state: State
) {
    enum class State {
        NEW, PROCESSING, COMPLETED
    }
}
