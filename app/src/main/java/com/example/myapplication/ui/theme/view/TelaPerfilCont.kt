package com.example.myapplication.ui.theme.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.viewModel.PerfilViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPerfilCont(
    navController: NavHostController,
    viewModel: PerfilViewModel
) {
    val begeFundo = Color(0xFFF3EFE0)
    val verde = Color(0xFF388E3C)

    val uiState by viewModel.uiState.collectAsState()
    val contexto = LocalContext.current

    var nome by remember(uiState.perfil.nome) { mutableStateOf(uiState.perfil.nome) }
    var email by remember(uiState.perfil.email) { mutableStateOf(uiState.perfil.email) }

    LaunchedEffect(uiState.erro) {
        if (!uiState.erro.isNullOrBlank()) {
            Toast.makeText(contexto, uiState.erro, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        containerColor = begeFundo,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (uiState.perfil.nome.isBlank()) "Fazer cadastro" else "Editar perfil",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Text(
                text = if (uiState.perfil.nome.isBlank()) "Crie seu perfil" else "Atualize seus dados",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (uiState.perfil.nome.isBlank()) "Preencha seus dados para começar." else "Altere seus dados quando quiser.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(text = "Nome", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                placeholder = { Text("Digite seu nome") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "E-mail", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                placeholder = { Text("Digite seu e-mail") }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.salvarPerfil(nome = nome, email = email) {
                        navController.popBackStack()
                    }
                },
                enabled = !uiState.salvando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = verde)
            ) {
                if (uiState.salvando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = if (uiState.perfil.nome.isBlank()) "Criar meu perfil" else "Salvar alterações",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}