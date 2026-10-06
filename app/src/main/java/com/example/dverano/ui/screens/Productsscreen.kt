package com.example.dverano.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dverano.R
import com.example.dverano.data.model.Producto
import com.example.dverano.ui.components.BarraNavegacion
import com.example.dverano.ui.components.BarraSuperior
import com.example.dverano.ui.components.EncabezadoSeccion
import com.example.dverano.ui.components.EtiquetaEstado
import com.example.dverano.ui.components.ProductoImagen
import com.example.dverano.ui.theme.BordeCampo
import com.example.dverano.ui.theme.DVeranoTheme
import com.example.dverano.ui.theme.FondoPantalla
import com.example.dverano.ui.theme.TextoSecundario
import com.example.dverano.ui.theme.VerdeOscuro
import com.example.dverano.ui.util.formatearPrecio

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    productos: List<Producto>,
    onAgregarProducto: () -> Unit,
    onNavegar: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        BarraSuperior(titulo = "Productos")

        EncabezadoSeccion(
            titulo = "Mis productos",
            descripcion = "Administra los platos de tu carta.",
            icono = R.drawable.ic_food,
        )

        Box(modifier = Modifier.weight(1f)) {
            if (productos.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    text = "Aún no tienes productos.\nToca + para agregar el primero.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario,
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productos, key = { it.id }) { producto ->
                        ProductoItem(producto = producto)
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = onAgregarProducto,
                shape = CircleShape,
                containerColor = VerdeOscuro,
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Agregar producto",
                )
            }
        }

        BarraNavegacion(itemSeleccionado = 1, onNavegar = onNavegar)
    }
}

@Composable
private fun ProductoItem(producto: Producto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeCampo)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProductoImagen(
                producto = producto,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = producto.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundario
                )
                Text(
                    text = formatearPrecio(producto.precio),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = VerdeOscuro
                )
            }
            EtiquetaEstado(
                activo = producto.disponible,
                textoActivo = "Disponible",
                textoInactivo = "Agotado",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    DVeranoTheme {
        ProductsScreen(
            modifier = Modifier.fillMaxSize(),
            productos = listOf(
                Producto(1, "Hamburguesa Especial", "Platos de fondo", 15.0, "", true, imagenRes = R.drawable.img_comida1),
                Producto(2, "Ensalada Fresca", "Entradas", 12.0, "", false, imagenRes = R.drawable.img_comida3),
            ),
            onAgregarProducto = {},
            onNavegar = {},
        )
    }
}