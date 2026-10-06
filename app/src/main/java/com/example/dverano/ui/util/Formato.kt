package com.example.dverano.ui.util

import java.util.Locale

fun formatearPrecio(precio: Double): String {
    return "S/ %.2f".format(Locale.US, precio)
}

fun String.aPrecioOrNull(): Double? {
    return this.replace(',', '.').toDoubleOrNull()
}

fun esPrecioParcialValido(texto: String): Boolean {
    if (texto.isEmpty()) return true
    return texto.matches(Regex("""^\d*(?:[.,]\d{0,2})?$"""))
}
