package com.example.dverano.data.model

import android.net.Uri
import androidx.annotation.DrawableRes

val CATEGORIAS = listOf("Entradas", "Comidas rápidas", "Platos principales", "Bebidas", "Postres")

data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val descripcion: String,
    val disponible: Boolean,
    val imagenUri: Uri? = null,               // imagen elegida de la galería
    @DrawableRes val imagenRes: Int? = null,  // imagen de ejemplo (drawable)
)

data class Oferta(
    val id: Int,
    val producto: Producto,
    val precioOferta: Double,
    val activa: Boolean,
) {
    val precioOriginal: Double get() = producto.precio
}