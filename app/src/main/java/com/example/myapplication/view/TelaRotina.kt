
package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.navigation.RotaAbas
import com.example.myapplication.model.Atividade
import com.example.myapplication.model.Rotina
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TelaRotina(
    navController: NavHostController,
    idRotina: Int?
) {
    val fundoBege = Color(0xFFF3EFE0)

    val scope = rememberCoroutineScope()

    var rotina by remember(idRotina) {
        mutableStateOf<Rotina?>(null)
    }

    val atividadesDaRotina = remember(idRotina) {
        mutableStateListOf<Atividade>()
    }

    val atividadesConcluidas = remember(idRotina) {
        mutableStateMapOf<Int, Boolean>()
    }

    var carregando by remember(idRotina) {
        mutableStateOf(true)
    }

    var erroCarregamento by remember(idRotina) {
        mutableStateOf(false)
    }

    // Carrega a rotina e suas atividades diretamente do Room.
    LaunchedEffect(idRotina) {
        rotina = null
        atividadesDaRotina.clear()
        atividadesConcluidas.clear()
        carregando = true
        erroCarregamento = false

        if (idRotina == null) {
            carregando = false
        } else {
            try {
                val resultado = withContext(Dispatchers.IO) {
                    val db = BancoDadosProvider.db

                    val rotinaEncontrada =
                        db.rotinaDAO()
                            .buscarTodas()
                            .find { it.idRotina == idRotina }

                    val atividades =
                        db.atividadeDAO()
                            .buscarPorRotina(idRotina)

                    Pair(rotinaEncontrada, atividades)
                }

                rotina = resultado.first
                atividadesDaRotina.addAll(resultado.second)
            } catch (e: Exception) {
                erroCarregamento = true
            } finally {
                carregando = false
            }
        }
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
                    .clickable {
                        navController.popBackStack()
                    },
                shape = CircleShape,
                color = fundoBege
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // CABEÇALHO DA ROTINA
        if (rotina != null) {
            Text(
                text = rotina!!.nomeRotina,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "${rotina!!.tempoMinutosRotina} minutos",
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

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // LISTA DE ATIVIDADES
        when {
            carregando -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Carregando atividades...",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }

            erroCarregamento -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Não foi possível carregar as atividades.",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }

            atividadesDaRotina.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhuma atividade cadastrada nesta rotina.",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
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
                        items = atividadesDaRotina,
                        key = { it.idAtividade }
                    ) { atividade ->

                        val concluida =
                            atividadesConcluidas[atividade.idAtividade]
                                ?: false

                        CardTarefa(
                            titulo = atividade.nomeAtividade,
                            descricao =
                                "Tempo: ${atividade.duracaoMinutos.toInt()} min",
                            isConcluida = concluida,

                            onCheckClick = {
                                atividadesConcluidas[
                                    atividade.idAtividade
                                ] = !concluida
                            },

                            onDeleteClick = {
                                scope.launch {
                                    try {
                                        withContext(Dispatchers.IO) {
                                            BancoDadosProvider.db
                                                .atividadeDAO()
                                                .excluir(atividade)
                                        }

                                        atividadesDaRotina.removeAll {
                                            it.idAtividade ==
                                                    atividade.idAtividade
                                        }

                                        atividadesConcluidas.remove(
                                            atividade.idAtividade
                                        )
                                    } catch (e: Exception) {
                                        erroCarregamento = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // BOTÃO EDITAR
        BotaoEditar(
            onClick = {
                if (idRotina != null) {
                    navController.navigate(
                        "${RotaAbas.TelaCriacao}/editar/$idRotina"
                    )
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
        colors = CardDefaults.cardColors(
            containerColor = verdeCard
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // EXCLUIR E INFORMAÇÕES DA ATIVIDADE
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            onDeleteClick()
                        },
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.25f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir atividade",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = descricao,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            // MARCAR COMO CONCLUÍDA
            Icon(
                imageVector = if (isConcluida) {
                    Icons.Default.CheckCircle
                } else {
                    Icons.Outlined.CheckCircle
                },
                contentDescription = if (isConcluida) {
                    "Concluído"
                } else {
                    "Pendente"
                },
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onCheckClick()
                    }
            )
        }
    }
}

@Composable
fun BotaoEditar(
    onClick: () -> Unit
) {
    val vermelhoBotao = Color(0xFFFF5252)

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = vermelhoBotao
        )
    ) {
        Text(
            text = "Editar",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
