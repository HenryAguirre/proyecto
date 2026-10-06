package com.example.dverano.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.dverano.R
import com.example.dverano.data.model.Oferta
import com.example.dverano.data.model.Producto

class CatalogoViewModel : ViewModel() {

    private val _productos = mutableStateListOf<Producto>()
    private val _ofertas = mutableStateListOf<Oferta>()

    val productos: List<Producto> get() = _productos
    val ofertas: List<Oferta> get() = _ofertas

    private var siguienteProductoId = 1
    private var siguienteOfertaId = 1

    init {
        // Datos de ejemplo (los mismos de tu diseño de Ofertas). Borra este bloque para empezar vacío.
        agregarProducto(
            Producto(0, "Hamburguesa Especial", "Comidas rápidas", 15.0, "Hamburguesa con papas", true, imagenRes = R.drawable.img_comida1)
        )
        agregarProducto(
            Producto(0, "Combo Familiar", "Platos principales", 55.0, "Para 4 personas", true, imagenRes = R.drawable.img_comida2)
        )
        agregarProducto(
            Producto(0, "Ensalada Fresca", "Entradas", 12.0, "Verduras de temporada", true, imagenRes = R.drawable.img_comida3)
        )
        // Producto sin oferta, para poder probar "Nueva oferta"
        agregarProducto(
            Producto(0, "Ceviche de pescado", "Entradas", 25.0, "Pescado fresco al limón", true)
        )
        agregarOferta(Oferta(0, _productos[0], 12.0, true))
        agregarOferta(Oferta(0, _productos[1], 45.0, true))
        agregarOferta(Oferta(0, _productos[2], 9.0, false))
    }

    /** El id que venga en [producto] se ignora: el ViewModel asigna uno nuevo. */
    fun agregarProducto(producto: Producto) {
        _productos.add(producto.copy(id = siguienteProductoId++))
    }

    /** El id que venga en [oferta] se ignora: el ViewModel asigna uno nuevo. */
    fun agregarOferta(oferta: Oferta) {
        // Una sola oferta por producto
        if (_ofertas.any { it.producto.id == oferta.producto.id && oferta.producto.id != 0 }) return
        _ofertas.add(oferta.copy(id = siguienteOfertaId++))
    }

    /** Productos que todavía no tienen una oferta. */
    fun productosSinOferta(): List<Producto> =
        _productos.filter { p -> _ofertas.none { it.producto.id == p.id } }

    fun cambiarEstadoOferta(id: Int) {
        val indice = _ofertas.indexOfFirst { it.id == id }
        if (indice >= 0) {
            _ofertas[indice] = _ofertas[indice].copy(activa = !_ofertas[indice].activa)
        }
    }
}