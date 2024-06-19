package com.brand.store.data


class Wallet(var balance: Double) {
    fun deductBalance(amount: Double) {
        balance -= amount
    }
}

