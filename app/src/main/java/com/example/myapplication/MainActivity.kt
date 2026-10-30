package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.carregarDadosSalvos
import com.example.myapplication.navigation.AppNavegacao
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.view.TelaCarregamento
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        BancoDadosProvider.inicializar(this)

        setContent {
            MyApplicationTheme {
                TelaCarregamento()
            }
        }

        lifecycleScope.launch {
            try {
                carregarDadosSalvos()

                setContent {
                    MyApplicationTheme {
                        AppNavegacao()
                    }
                }
            } catch (e: Exception) {
                Log.e("RotiApp", "Erro ao carregar dados", e)
            }
        }
    }
}