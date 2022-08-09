package com.mirfanrafif.siwarung.utils

import java.text.NumberFormat
import java.util.*

object CurrencyHelper {
    fun formatPrice(price: Int): String {
        return NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            .also { it.maximumFractionDigits = 0 }.format(price)
    }
}