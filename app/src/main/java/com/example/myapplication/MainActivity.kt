
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.carregarDadosSalvos

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        BancoDadosProvider.inicializar(this)
        carregarDadosSalvos()

        setContent {
            MyApplicationTheme {
                AppNavegacao()
            }
        }
    }
}

