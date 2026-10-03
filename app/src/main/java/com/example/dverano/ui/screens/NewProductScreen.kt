package com.example.dverano.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dverano.R
import com.example.dverano.ui.theme.DVeranoTheme

private val VerdeOscuro = Color(0xFF1B3A28)
private val FondoPantalla = Color(0xFFF7F7F7)
private val BordeCampo = Color(0xFFE0E0E0)
private val TextoSecundario = Color(0xFF6B6B6B)

@Composable
fun NewProductScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onGuardarSuccess: () -> Unit,
) {
    val context = LocalContext.current

    val categorias = listOf("Entradas", "Platos de fondo", "Bebidas", "Postres")

    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var categoriaExpandida by remember { mutableStateOf(false) }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var disponible by remember { mutableStateOf(true) }

    var errorNombre by remember { mutableStateOf(false) }
    var errorCategoria by remember { mutableStateOf(false) }
    var errorPrecio by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        imagenUri = uri
    }

    val colorCampos = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        unfocusedBorderColor = BordeCampo,
        focusedBorderColor = VerdeOscuro,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        // Barra superior
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
                text = "Nuevo producto",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFBDBDBD),
                            style = Stroke(
                                width = 2.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 12f))
                            ),
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )
                    }
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = null,
                    tint = VerdeOscuro,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = if (imagenUri == null) "Agregar imagen del producto" else "Imagen seleccionada",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = VerdeOscuro
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Nombre del producto",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundario
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        if (nombre.isNotBlank()) {
                            errorNombre = false
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Ej. Ceviche de pescado"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_food),
                            contentDescription = null,
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = colorCampos,
                    isError = errorNombre,
                    supportingText = {
                        if (errorNombre) {
                            Text(
                                text = "El nombre es obligatorio"
                            )
                        } else {
                            null
                        }
                    },
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Categoría",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundario
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = categoria,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = {
                            Text(
                                text = "Selecciona una categoría"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_menu),
                                contentDescription = null,
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_down),
                                contentDescription = null,
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = colorCampos,
                        isError = errorCategoria,
                        supportingText = {
                            if (errorCategoria) {
                                Text(
                                    text = "Selecciona una categoría"
                                )
                            } else {
                                null
                            }
                        },
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { categoriaExpandida = true }
                    )
                    DropdownMenu(
                        expanded = categoriaExpandida,
                        onDismissRequest = { categoriaExpandida = false }
                    ) {
                        categorias.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item
                                    )
                                },
                                onClick = {
                                    categoria = item
                                    errorCategoria = false
                                    categoriaExpandida = false
                                }
                            )
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Precio (S/)",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundario
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = precio,
                    onValueChange = {
                        precio = it
                        if (precio.toDoubleOrNull() != null) {
                            errorPrecio = false
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Ej. 25.00"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_card),
                            contentDescription = null,
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = colorCampos,
                    isError = errorPrecio,
                    supportingText = {
                        if (errorPrecio) {
                            if (precio.isEmpty()) {
                                Text(
                                    text = "El precio es obligatorio"
                                )
                            } else if (precio.toDoubleOrNull() == null) {
                                Text(
                                    text = "Ingrese un precio válido"
                                )
                            }
                        } else {
                            null
                        }
                    },
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextoSecundario
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    placeholder = {
                        Text(
                            text = "Describe los ingredientes, porciones..."
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = null,
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = colorCampos,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Disponible",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = disponible,
                    onCheckedChange = { disponible = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = VerdeOscuro,
                        checkedThumbColor = Color.White,
                    )
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VerdeOscuro),
            onClick = {
                if (nombre.isBlank()) {
                    errorNombre = true
                }
                if (categoria.isEmpty()) {
                    errorCategoria = true
                }
                if (precio.toDoubleOrNull() == null) {
                    errorPrecio = true
                }
                if (!errorNombre && !errorCategoria && !errorPrecio) {
                    onGuardarSuccess()
                } else {
                    Toast.makeText(context, "Revisa los campos marcados", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                text = "Guardar producto",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewProductScreenPreview() {
    DVeranoTheme {
        NewProductScreen(
            modifier = Modifier.fillMaxSize(),
            onBack = {},
            onGuardarSuccess = {},
        )
    }
}