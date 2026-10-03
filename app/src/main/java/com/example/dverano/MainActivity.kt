package com.example.dverano

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.dverano.ui.screens.NewProductScreen
import com.example.dverano.ui.theme.DVeranoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DVeranoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewProductScreen(
                        modifier = Modifier.padding(innerPadding),
                        onBack = { finish() },
                        onGuardarSuccess = {
                            Toast.makeText(
                                this@MainActivity,
                                "Producto guardado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }

}