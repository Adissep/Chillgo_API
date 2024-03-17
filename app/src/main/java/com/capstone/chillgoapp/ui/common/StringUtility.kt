package com.capstone.chillgoapp.ui.common

import java.util.Locale

object StringUtility {
    fun String.capital() =
        this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}