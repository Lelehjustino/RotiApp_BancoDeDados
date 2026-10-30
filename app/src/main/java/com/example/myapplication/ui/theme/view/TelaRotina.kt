package com.example.myapplication.ui.theme.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
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
import com.example.myapplication.viewmodel.RotinaViewModel
import com.example.myapplication.viewmodel.uistate.RotinaUiState

@Composable
fun TelaRotina(
    navController: NavHostController,
    idRotina: Int?,
    viewModel: RotinaViewModel = viewModel()
) {
    val fundoBege = Color(0xFFF3EFE0)

    val uiState: RotinaUiState by viewModel.uiState.collectAsState()

    val carregando: Boolean = uiState.carregando
    val erroCarregamento: Boolean = uiState.erroCarregamento
    val rotina = uiState.rotina

    // Carrega os dados sempre que o idRotina mudar
    LaunchedEffect(idRotina) {
        viewModel.carregarDados(idRotina)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fundoBege)
            .padding(20.dp)
    ) {
        // BOTÃO VOLTAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.popBackStack() },
                shape = CircleShape,
                color = fundoBege
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // CABEÇALHO DA ROTINA
        if (rotina != null) {
            Text(
                text = rotina.nomeRotina,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${rotina.tempoMinutosRotina} minutos",
                fontSize = 16.sp,
                color = Color.Black
            )
        } else if (carregando) {
            Text(
                text = "Carregando rotina...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        } else {
            Text(
                text = "Rotina não encontrada",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // LISTA DE ATIVIDADES
        when {
            carregando -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Carregando atividades...", color = Color.Black, fontSize = 16.sp)
                }
            }

            erroCarregamento -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Não foi possível carregar as atividades.", color = Color.Black, fontSize = 16.sp)
                }
            }

            uiState.atividadesDaRotina.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Nenhuma atividade cadastrada nesta rotina.", color = Color.Black, fontSize = 16.sp)
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = uiState.atividadesDaRotina,
                        key = { it.idAtividade }
                    ) { atividade ->

                        val concluida =
                            atividade.idAtividade in uiState.atividadesConcluidas

                        CardTarefa(
                            titulo = atividade.nomeAtividade,
                            descricao = "Tempo: ${atividade.duracaoMinutos.toInt()} min",
                            isConcluida = concluida,
                            onCheckClick = {
                                viewModel.alternarConclusao(atividade.idAtividade)
                            },
                            onDeleteClick = {
                                viewModel.excluirAtividade(atividade)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BOTÃO EDITAR
        BotaoEditar(
            onClick = {
                if (idRotina != null) {
                    navController.navigate("${RotaAbas.TelaCriacao}/editar/$idRotina")
                }
            }
        )
    }
}

@Composable
fun CardTarefa(
    titulo: String,
    descricao: String,
    isConcluida: Boolean,
    onDeleteClick: () -> Unit = {},
    onCheckClick: () -> Unit = {}
) {
    val verdeCard = Color(0xFF388E3C)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = verdeCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { onDeleteClick() },
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.25f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir atividade",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = descricao, fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f))
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = if (isConcluida) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                contentDescription = if (isConcluida) "Concluído" else "Pendente",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onCheckClick() }
            )
        }
    }
}

@Composable
fun BotaoEditar(onClick: () -> Unit) {
    val vermelhoBotao = Color(0xFFFF5252)

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = vermelhoBotao)
    ) {
        Text(text = "Editar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}