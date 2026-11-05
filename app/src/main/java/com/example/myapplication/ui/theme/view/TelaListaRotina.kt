package com.example.myapplication.ui.theme.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.navigation.RotaAbas
import com.example.myapplication.viewModel.TelaCriacaoViewModel
import com.example.myapplication.model.listaRotinas

@Composable
fun TelaListaRotinas(
    navController: NavHostController,
    viewModel: TelaCriacaoViewModel = viewModel()
) {
    val begeFundo = Color(0xFFF3EFE0)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = begeFundo
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "RotiApp",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = listaRotinas,
                    key = { rotina -> rotina.idRotina }
                ) { rotina ->
                    CardRotina(
                        idRotina = rotina.idRotina,
                        titulo = rotina.nomeRotina,
                        descricao = "${rotina.tempoMinutosRotina} min",
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(RotaAbas.TelaCriacao) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                Text(
                    text = "+ Nova rotina",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CardRotina(
    idRotina: Int,
    titulo: String,
    descricao: String,
    navController: NavHostController,
    viewModel: TelaCriacaoViewModel
) {
    val vermelhoCard = Color(0xFFFF5252)
    var mostrarConfirmacao by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = vermelhoCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("${RotaAbas.TelaRotina}/$idRotina") }
                    .padding(vertical = 4.dp)
            ) {
                Text(text = titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = descricao, fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }

            Spacer(modifier = Modifier.size(12.dp))

            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { mostrarConfirmacao = true },
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.4f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    IconButton(onClick = { mostrarConfirmacao = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir rotina",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }

    if (mostrarConfirmacao) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacao = false },
            title = { Text(text = "Excluir rotina?") },
            text = { Text(text = "A rotina \"$titulo\" e todas as atividades dela serão removidas.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.excluirRotina(idRotina)
                        mostrarConfirmacao = false
                    }
                ) {
                    Text(text = "Excluir", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacao = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}