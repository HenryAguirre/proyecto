package com.example.dverano

import android.os.Bundle
import com.example.dverano.ui.navigation.DVeranoApp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.dverano.ui.theme.DVeranoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DVeranoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DVeranoApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

}