package com.example.myapplication.ui.theme.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.PerfilViewModel
import com.example.myapplication.navigation.RotaAbas

@Composable
fun TelaPerfil(
    navController: NavHostController,
    viewModel: PerfilViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val begeFundo = Color(0xFFF3EFE0)
    val verde = Color(0xFF388E3C)
    val verdeClaro = Color(0xFF7D8C7A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(begeFundo)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Meu perfil", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(verde),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                tint = Color.White,
                modifier = Modifier.size(55.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (uiState.carregando) {
            CircularProgressIndicator(color = verde)
        } else if (uiState.perfil.nome.isBlank()) {
            Text(text = "Ainda não possui um cadastro?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Faça seu registro para personalizar seu perfil e acompanhar melhor sua rotina.",
                fontSize = 14.sp,
                color = verdeClaro,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(RotaAbas.TelaPerfilCont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = verde)
            ) {
                Text(text = "Fazer meu cadastro", fontSize = 17.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            Text(text = "Olá, ${uiState.perfil.nome}!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = "Seu perfil está pronto.", fontSize = 14.sp, color = verdeClaro)

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Informações", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = verde, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.size(12.dp))
                        Column {
                            Text(text = "Nome", fontSize = 12.sp, color = Color.Gray)
                            Text(text = uiState.perfil.nome, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = verde, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.size(12.dp))
                        Column {
                            Text(text = "E-mail", fontSize = 12.sp, color = Color.Gray)
                            Text(text = uiState.perfil.email, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(RotaAbas.TelaPerfilCont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = verde)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Editar perfil", fontSize = 17.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}