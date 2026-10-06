package com.example.dverano.ui.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.dverano.R
import com.example.dverano.data.model.Producto
import com.example.dverano.ui.theme.VerdeClaro
import com.example.dverano.ui.theme.VerdeOscuro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProductoImagen(
    producto: Producto,
    modifier: Modifier = Modifier,
) {
    ProductoImagen(
        imagenUri = producto.imagenUri,
        imagenRes = producto.imagenRes,
        contentDescription = producto.nombre,
        modifier = modifier,
    )
}

/**
 * Muestra la imagen de la galería (Uri) o un drawable. Si no hay ninguna, muestra un marcador.
 * Sin librerías externas: la Uri se decodifica en un hilo de fondo.
 */
@Composable
fun ProductoImagen(
    imagenUri: Uri?,
    @DrawableRes imagenRes: Int?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    when {
        imagenUri != null -> {
            val bitmap by produceState<ImageBitmap?>(initialValue = null, imagenUri) {
                value = withContext(Dispatchers.IO) {
                    runCatching {
                        val opciones = BitmapFactory.Options().apply { inSampleSize = 4 }
                        context.contentResolver.openInputStream(imagenUri)?.use { stream ->
                            BitmapFactory.decodeStream(stream, null, opciones)?.asImageBitmap()
                        }
                    }.getOrNull()
                }
            }
            val imagen = bitmap
            if (imagen != null) {
                Image(
                    modifier = modifier,
                    bitmap = imagen,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
            } else {
                MarcadorImagen(modifier)
            }
        }

        imagenRes != null -> Image(
            modifier = modifier,
            painter = painterResource(imagenRes),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        else -> MarcadorImagen(modifier)
    }
}

@Composable
private fun MarcadorImagen(modifier: Modifier) {
    Box(
        modifier = modifier.background(VerdeClaro),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_food),
            contentDescription = null,
            tint = VerdeOscuro,
        )
    }
}