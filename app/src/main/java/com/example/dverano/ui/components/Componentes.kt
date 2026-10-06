package com.example.dverano.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dverano.R
import com.example.dverano.ui.theme.RojoClaro
import com.example.dverano.ui.theme.RojoOscuro
import com.example.dverano.ui.theme.TextoSecundario
import com.example.dverano.ui.theme.VerdeClaro
import com.example.dverano.ui.theme.VerdeOscuro

/** Barra superior. Si [onBack] es null no muestra la flecha (pantallas raíz). */
@Composable
fun BarraSuperior(
    titulo: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .heightIn(min = 48.dp)
    ) {
        if (onBack != null) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Volver",
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = titulo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EncabezadoSeccion(
    titulo: String,
    descripcion: String,
    @DrawableRes icono: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
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
                painter = painterResource(icono),
                contentDescription = null,
                tint = VerdeOscuro,
            )
        }
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun EtiquetaEstado(
    activo: Boolean,
    textoActivo: String = "Activo",
    textoInactivo: String = "Inactivo",
) {
    Box(
        modifier = Modifier
            .background(
                color = if (activo) VerdeClaro else RojoClaro,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = if (activo) textoActivo else textoInactivo,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (activo) VerdeOscuro else RojoOscuro
        )
    }
}

private data class ItemNavegacion(
    val titulo: String,
    @DrawableRes val icono: Int,
)

/** Índices: 0 Inicio, 1 Productos, 2 Ofertas, 3 Perfil. */
@Composable
fun BarraNavegacion(
    itemSeleccionado: Int,
    onNavegar: (Int) -> Unit,
) {
    val items = remember {
        listOf(
            ItemNavegacion("Inicio", R.drawable.ic_home),
            ItemNavegacion("Productos", R.drawable.ic_food),
            ItemNavegacion("Ofertas", R.drawable.ic_offer),
            ItemNavegacion("Perfil", R.drawable.ic_person),
        )
    }
    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == itemSeleccionado,
                onClick = { onNavegar(index) },
                icon = {
                    Icon(
                        painter = painterResource(item.icono),
                        contentDescription = item.titulo,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeOscuro,
                    unselectedIconColor = TextoSecundario,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}