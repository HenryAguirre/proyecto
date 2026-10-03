package com.example.dverano.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dverano.R
import com.example.dverano.ui.theme.DVeranoTheme
import java.util.Locale

private val VerdeOscuro = Color(0xFF1B3A28)
private val FondoPantalla = Color(0xFFF7F7F7)
private val BordeCard = Color(0xFFE0E0E0)
private val TextoSecundario = Color(0xFF6B6B6B)
private val VerdeClaro = Color(0xFFE3F0E6)
private val RojoClaro = Color(0xFFFDE4E4)
private val RojoOscuro = Color(0xFFC62828)

data class Oferta(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precioOferta: Double,
    val precioOriginal: Double,
    val activa: Boolean,
    @DrawableRes val imagen: Int,
)

private data class ItemNavegacion(
    val titulo: String,
    @DrawableRes val icono: Int,
)

@Composable
fun OffersScreen(
    modifier: Modifier = Modifier,
    ofertas: List<Oferta>,
    onBack: () -> Unit,
    onAgregarOferta: () -> Unit,
    onOfertaClick: (Oferta) -> Unit,
    onNavegar: (Int) -> Unit,
) {
    val itemsNavegacion = remember {
        listOf(
            ItemNavegacion("Inicio", R.drawable.ic_home),
            ItemNavegacion("Productos", R.drawable.ic_food),
            ItemNavegacion("Ofertas", R.drawable.ic_offer),
            ItemNavegacion("Perfil", R.drawable.ic_person),
        )
    }
    val itemSeleccionado = 2

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Volver",
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Ofertas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = VerdeOscuro,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(VerdeClaro, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_offer),
                    contentDescription = null,
                    tint = VerdeOscuro,
                )
            }
            Column {
                Text(
                    text = "Productos en promoción",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Gestiona los platos que estarán disponibles en la sección de ofertas.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ofertas, key = { it.id }) { oferta ->
                    OfertaItem(
                        oferta = oferta,
                        onClick = { onOfertaClick(oferta) }
                    )
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = onAgregarOferta,
                shape = CircleShape,
                containerColor = VerdeOscuro,
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Agregar oferta",
                )
            }
        }

        NavigationBar(containerColor = Color.White) {
            itemsNavegacion.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == itemSeleccionado,
                    onClick = { onNavegar(index) },
                    icon = {
                        Icon(
                            painter = painterResource(item.icono),
                            contentDescription = null,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VerdeOscuro,
                        selectedTextColor = VerdeOscuro,
                        unselectedIconColor = TextoSecundario,
                        unselectedTextColor = TextoSecundario,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
private fun OfertaItem(
    oferta: Oferta,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeCard)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(oferta.imagen),
                contentDescription = oferta.nombre,
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = oferta.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = oferta.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundario
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = formatearPrecio(oferta.precioOferta),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = VerdeOscuro
                    )
                    Text(
                        text = formatearPrecio(oferta.precioOriginal),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextoSecundario,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
            EstadoChip(activa = oferta.activa)
        }
    }
}

@Composable
private fun EstadoChip(activa: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = if (activa) VerdeClaro else RojoClaro,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = if (activa) "Activo" else "Inactivo",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (activa) VerdeOscuro else RojoOscuro
        )
    }
}

private fun formatearPrecio(valor: Double): String {
    return String.format(Locale.US, "S/ %.2f", valor)
}

@Preview(showBackground = true)
@Composable
fun OffersScreenPreview() {
    DVeranoTheme {
        OffersScreen(
            modifier = Modifier.fillMaxSize(),
            ofertas = listOf(
                Oferta(1, "Hamburguesa Especial", "Comidas rápidas", 12.0, 15.0, true, R.drawable.img_comida1),
                Oferta(2, "Combo Familiar", "Platos principales", 45.0, 55.0, true, R.drawable.img_comida2),
                Oferta(3, "Ensalada Fresca", "Entradas", 9.0, 12.0, false, R.drawable.img_comida3),
            ),
            onBack = {},
            onAgregarOferta = {},
            onOfertaClick = {},
            onNavegar = {},
        )
    }
}