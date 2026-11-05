package com.example.myapplication.ui.theme.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.DesempenhoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDesempenho(
    navController: NavHostController,
    viewModel: DesempenhoViewModel = viewModel()
) {
    val bege = Color(0xFFF3EFE0)
    val verde = Color(0xFF388E3C)
    val verdeClaro = Color(0xFF7D8C7A)
    val vermelho = Color(0xFFFF5252)

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.atualizarDados()
    }

    Scaffold(
        containerColor = bege,
        topBar = {
            TopAppBar(
                title = { Text(text = "Meu desempenho", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Seu progresso", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Acompanhe sua evolução com base nas suas rotinas.", fontSize = 14.sp, color = verdeClaro)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = verde)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Nível ${uiState.nivel}",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${uiState.xpNoNivel} / 100 XP",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 14.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(55.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        LinearProgressIndicator(
                            progress = { uiState.progressoNivel },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    EstatisticaCard(
                        titulo = "Rotinas",
                        valor = uiState.quantidadeRotinas.toString(),
                        icon = Icons.Default.List,
                        modifier = Modifier.weight(1f)
                    )

                    EstatisticaCard(
                        titulo = "Atividades",
                        valor = uiState.quantidadeAtividades.toString(),
                        icon = Icons.Default.CheckCircle,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    EstatisticaCard(
                        titulo = "Tempo total",
                        valor = "${uiState.tempoTotal} min",
                        icon = Icons.Default.Check,
                        modifier = Modifier.weight(1f)
                    )

                    EstatisticaCard(
                        titulo = "Média",
                        valor = "${"%.1f".format(uiState.mediaTempoRotina)} min",
                        icon = Icons.Default.Check,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                val maior = uiState.maiorRotina

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "Rotina mais longa", fontSize = 15.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = maior?.nomeRotina ?: "Nenhuma rotina cadastrada",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (maior != null) {
                            Text(
                                text = "${maior.tempoMinutosRotina} minutos",
                                fontSize = 14.sp,
                                color = vermelho
                            )
                        }
                    }
                }
            }

            item {
                Text(text = "Suas rotinas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            if (uiState.rotinas.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "Você ainda não possui rotinas cadastradas.",
                            modifier = Modifier.padding(20.dp),
                            color = Color.Gray
                        )
                    }
                }
            } else {
                items(
                    items = uiState.rotinas,
                    key = { it.idRotina }
                ) { rotina ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = rotina.nomeRotina,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${rotina.tempoMinutosRotina} min",
                                    fontSize = 14.sp,
                                    color = verde
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${viewModel.atividadesDaRotina(rotina.idRotina).size} atividades",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            LinearProgressIndicator(
                                progress = { viewModel.percentualDaRotina(rotina) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun EstatisticaCard(
    titulo: String,
    valor: String,
    icon: ImageVector,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF388E3C),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = titulo, fontSize = 13.sp, color = Color.Gray)
            Text(text = valor, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }
    }
}