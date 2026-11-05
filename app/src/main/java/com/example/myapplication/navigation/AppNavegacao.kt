package com.example.myapplication.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.view.Progresso
import com.example.myapplication.ui.theme.view.TelaCriacao
import com.example.myapplication.ui.theme.view.TelaDesempenho
import com.example.myapplication.ui.theme.view.TelaHistorico
import com.example.myapplication.ui.theme.view.TelaListaRotinas
import com.example.myapplication.ui.theme.view.TelaMetas
import com.example.myapplication.ui.theme.view.TelaPerfil
import com.example.myapplication.ui.theme.view.TelaPerfilCont
import com.example.myapplication.ui.theme.view.TelaRotina
import com.example.myapplication.viewModel.PerfilViewModel
import com.example.myapplication.viewModel.TelaCriacaoViewModel

// ============================================================
// NAVEGAÇÃO PRINCIPAL
// ============================================================
@Composable
fun AppNavegacao() {

    val navController = rememberNavController()

    // UM ÚNICO ViewModel para as telas de perfil
    val perfilViewModel: PerfilViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF3EFE0),

        bottomBar = {
            BarraNavegacao(navController)
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = RotaAbas.TelaListaRotina,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // ==========================================
            // LISTA DE ROTINAS
            // ==========================================
            composable(RotaAbas.TelaListaRotina) {
                TelaListaRotinas(
                    navController = navController
                )
            }

            // ==========================================
            // PROGRESSO
            // ==========================================
            composable(RotaAbas.Progresso) {
                Progresso(
                    navController = navController
                )
            }

            // ==========================================
            // PERFIL
            // ==========================================
            composable(RotaAbas.TelaPerfil) {
                TelaPerfil(
                    navController = navController,
                    viewModel = perfilViewModel
                )
            }

            // ==========================================
            // EDITAR / CADASTRAR PERFIL
            // ==========================================
            composable(RotaAbas.TelaPerfilCont) {
                TelaPerfilCont(
                    navController = navController,
                    viewModel = perfilViewModel
                )
            }

            // ==========================================
            // NOVA ROTINA
            // ==========================================
            composable(
                route = RotaAbas.TelaCriacao
            ) {
                val viewModel: TelaCriacaoViewModel = viewModel()
                TelaCriacao(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            // ==========================================
            // EDITAR ROTINA
            // ==========================================
            composable(
                route = "${RotaAbas.TelaCriacao}/editar/{idRotina}"
            ) { backStackEntry ->
                val id = backStackEntry
                    .arguments
                    ?.getString("idRotina")
                    ?.toIntOrNull()

                val viewModel: TelaCriacaoViewModel = viewModel()

                if (id != null) {
                    viewModel.carregarRotinaParaEditar(id)
                }

                TelaCriacao(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            // ==========================================
            // DETALHES DA ROTINA
            // ==========================================
            composable(
                route = "${RotaAbas.TelaRotina}/{idRotina}"
            ) { backStackEntry ->
                val idRotina = backStackEntry
                    .arguments
                    ?.getString("idRotina")
                    ?.toIntOrNull()

                TelaRotina(
                    navController = navController,
                    idRotina = idRotina
                )
            }

            // ==========================================
            // HISTÓRICO
            // ==========================================
            composable(RotaAbas.TelaHistorico) {
                TelaHistorico(
                    navController = navController
                )
            }

            // ==========================================
            // METAS
            // ==========================================
            composable(RotaAbas.TelaMetas) {
                TelaMetas(
                    navController = navController
                )
            }

            // ==========================================
            // DESEMPENHO
            // ==========================================
            composable(RotaAbas.TelaDesempenho) {
                TelaDesempenho(
                    navController = navController
                )
            }
        }
    }
}

// ============================================================
// BARRA DE NAVEGAÇÃO INFERIOR
// ============================================================
@Composable
fun BarraNavegacao(
    navController: NavHostController
) {
    val rotaAtual = navController.currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFFF3EFE0)
    ) {
        NavigationBarItem(
            selected = rotaAtual == RotaAbas.TelaListaRotina,
            onClick = {
                navController.navigate(RotaAbas.TelaListaRotina) {
                    popUpTo(RotaAbas.TelaListaRotina) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Rotinas"
                )
            },
            label = {
                Text("Rotinas")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                selectedTextColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = rotaAtual == RotaAbas.Progresso,
            onClick = {
                navController.navigate(RotaAbas.Progresso) {
                    popUpTo(RotaAbas.TelaListaRotina) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Progresso"
                )
            },
            label = {
                Text("Progresso")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                selectedTextColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = rotaAtual == RotaAbas.TelaPerfil,
            onClick = {
                navController.navigate(RotaAbas.TelaPerfil) {
                    popUpTo(RotaAbas.TelaListaRotina) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50),
                selectedTextColor = Color(0xFF4CAF50),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
    }
}