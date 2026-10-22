package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.theme.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilViewModel : ViewModel() {

    var perfil = mutableStateOf(Perfil())
        private set

    var carregando = mutableStateOf(true)
        private set

    var erro = mutableStateOf<String?>(null)
        private set

    init {
        carregarPerfil()
    }

    fun possuiCadastro(): Boolean {
        return perfil.value.nome.isNotBlank() &&
                perfil.value.email.isNotBlank()
    }

    fun carregarPerfil() {
        viewModelScope.launch {
            carregando.value = true

            try {
                val perfilSalvo = withContext(Dispatchers.IO) {
                    BancoDadosProvider.db
                        .perfilDAO()
                        .buscarPerfil()
                }

                perfil.value = perfilSalvo ?: Perfil()
                erro.value = null

            } catch (e: Exception) {
                erro.value = "Não foi possível carregar o perfil."
            } finally {
                carregando.value = false
            }
        }
    }

    fun salvarPerfil(
        nome: String,
        email: String,
        aoSalvar: () -> Unit
    ) {
        val nomeLimpo = nome.trim()
        val emailLimpo = email.trim()

        if (nomeLimpo.isBlank() || emailLimpo.isBlank()) {
            erro.value = "Preencha o nome e o e-mail."
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(emailLimpo)
                .matches()
        ) {
            erro.value = "Digite um e-mail válido."
            return
        }

        viewModelScope.launch {
            try {
                erro.value = null

                val novoPerfil = Perfil(
                    id = 1,
                    nome = nomeLimpo,
                    email = emailLimpo
                )

                withContext(Dispatchers.IO) {
                    BancoDadosProvider.db
                        .perfilDAO()
                        .salvarPerfil(novoPerfil)
                }

                // Atualiza a tela somente depois de salvar no banco.
                perfil.value = novoPerfil
                erro.value = null

                aoSalvar()

            } catch (e: Exception) {
                    erro.value = "Erro ao salvar perfil: ${
                        e.localizedMessage ?: "erro desconhecido"
                    }"
            }
        }
    }
}