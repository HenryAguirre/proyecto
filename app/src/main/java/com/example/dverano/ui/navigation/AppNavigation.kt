package com.example.dverano.ui.navigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dverano.ui.screens.NewOfferScreen
import com.example.dverano.ui.screens.NewProductScreen
import com.example.dverano.ui.screens.OffersScreen
import com.example.dverano.ui.screens.ProductsScreen
import com.example.dverano.ui.viewmodel.CatalogoViewModel

enum class Pantalla { PRODUCTOS, NUEVO_PRODUCTO, OFERTAS, NUEVA_OFERTA }

@Composable
fun DVeranoApp(
    modifier: Modifier = Modifier,
    viewModel: CatalogoViewModel = viewModel(),
) {
    val context = LocalContext.current

    var pantalla by rememberSaveable { mutableStateOf(Pantalla.PRODUCTOS) }
    // Desde dónde se abrió "Nuevo producto" (lista de productos o "Nueva oferta")
    var origenNuevoProducto by rememberSaveable { mutableStateOf(Pantalla.PRODUCTOS) }

    fun volver() {
        pantalla = when (pantalla) {
            Pantalla.NUEVO_PRODUCTO -> origenNuevoProducto
            Pantalla.NUEVA_OFERTA -> Pantalla.OFERTAS
            else -> Pantalla.PRODUCTOS
        }
    }

    fun abrirNuevoProductoDesde(origen: Pantalla) {
        origenNuevoProducto = origen
        pantalla = Pantalla.NUEVO_PRODUCTO
    }

    fun navegarDesdeBarra(indice: Int) {
        when (indice) {
            1 -> pantalla = Pantalla.PRODUCTOS
            2 -> pantalla = Pantalla.OFERTAS
            // Conecta aquí tus pantallas de Inicio (0) y Perfil (3)
            else -> Toast.makeText(context, "Pantalla en construcción", Toast.LENGTH_SHORT).show()
        }
    }

    BackHandler(enabled = pantalla != Pantalla.PRODUCTOS) { volver() }

    when (pantalla) {
        Pantalla.PRODUCTOS -> ProductsScreen(
            modifier = modifier,
            productos = viewModel.productos.toList(),
            onAgregarProducto = { abrirNuevoProductoDesde(Pantalla.PRODUCTOS) },
            onNavegar = ::navegarDesdeBarra,
        )

        Pantalla.NUEVO_PRODUCTO -> NewProductScreen(
            modifier = modifier,
            onBack = ::volver,
            onGuardar = { producto ->
                viewModel.agregarProducto(producto)
                Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
                volver()
            },
        )

        Pantalla.OFERTAS -> OffersScreen(
            modifier = modifier,
            ofertas = viewModel.ofertas.toList(),
            onBack = ::volver,
            onAgregarOferta = { pantalla = Pantalla.NUEVA_OFERTA },
            onOfertaClick = { oferta -> viewModel.cambiarEstadoOferta(oferta.id) },
            onNavegar = ::navegarDesdeBarra,
        )

        Pantalla.NUEVA_OFERTA -> NewOfferScreen(
            modifier = modifier,
            productos = viewModel.productosSinOferta(),
            onBack = ::volver,
            onIrANuevoProducto = { abrirNuevoProductoDesde(Pantalla.NUEVA_OFERTA) },
            onGuardar = { oferta ->
                viewModel.agregarOferta(oferta)
                Toast.makeText(context, "Oferta guardada", Toast.LENGTH_SHORT).show()
                pantalla = Pantalla.OFERTAS
            },
        )
    }
}