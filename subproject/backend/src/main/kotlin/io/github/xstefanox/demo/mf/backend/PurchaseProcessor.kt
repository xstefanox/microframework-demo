package io.github.xstefanox.demo.mf.backend

import io.github.xstefanox.demo.mf.core.purchase.Purchase.State.COMPLETED
import io.github.xstefanox.demo.mf.core.purchase.PurchaseManager
import io.github.xstefanox.demo.mf.core.purchase.PurchaseMessage

class PurchaseProcessor(
    private val purchaseManager: PurchaseManager
) {

    fun complete(message: PurchaseMessage) {
        purchaseManager.setState(message.id, COMPLETED)
    }
}
