package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Atividade
import com.example.myapplication.model.Rotina
import com.example.myapplication.model.listaAtividades
import com.example.myapplication.model.listaRotinas
import com.example.myapplication.viewmodel.uistate.TelaCriacaoUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TelaCriacaoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TelaCriacaoUiState())
    val uiState: StateFlow<TelaCriacaoUiState> = _uiState.asStateFlow()

    fun alterarNomeRotina(valor: String) {
        _uiState.update { it.copy(nomeRotina = valor) }
    }

    fun alterarInicio(valor: String) {
        _uiState.update { it.copy(inicio = valor) }
    }

    fun alterarFim(valor: String) {
        _uiState.update { it.copy(fim = valor) }
    }

    fun alterarNomeAtividade(valor: String) {
        _uiState.update { it.copy(nomeAtividade = valor) }
    }

    fun alterarDuracaoAtividade(valor: String) {
        _uiState.update { it.copy(duracaoAtividade = valor.filter { char -> char.isDigit() }) }
    }

    fun adicionarAtividade(): Boolean {
        val currentState = _uiState.value
        val nome = currentState.nomeAtividade.trim()
        val duracao = currentState.duracaoAtividade.toDoubleOrNull()

        if (nome.isBlank() || duracao == null || duracao <= 0) {
            return false
        }

        val novoId = (currentState.atividades.maxOfOrNull { it.idAtividade } ?: 0) + 1

        val novaAtividade = Atividade(
            idAtividade = novoId,
            idRotina = currentState.idRotinaEditando ?: 0,
            nomeAtividade = nome,
            duracaoMinutos = duracao
        )

        val novaLista = currentState.atividades.toMutableList().apply {
            add(novaAtividade)
        }

        _uiState.update {
            it.copy(
                atividades = novaLista,
                nomeAtividade = "",
                duracaoAtividade = ""
            )
        }

        return true
    }

    fun removerAtividade(id: Int) {
        _uiState.update { state ->
            val novaLista = state.atividades.toMutableList().apply {
                removeAll { it.idAtividade == id }
            }
            state.copy(atividades = novaLista)
        }
    }

    suspend fun salvarRotina(): Rotina? {
        val currentState = _uiState.value
        val nome = currentState.nomeRotina.trim()

        if (nome.isBlank()) {
            return null
        }

        return try {
            val rotinaSalva = withContext(Dispatchers.IO) {
                val db = BancoDadosProvider.db
                val rotinaDAO = db.rotinaDAO()
                val atividadeDAO = db.atividadeDAO()

                val idExistente = currentState.idRotinaEditando

                val tempoTotal = currentState.atividades
                    .sumOf { it.duracaoMinutos }
                    .toInt()

                val rotina = Rotina(
                    idRotina = idExistente ?: 0,
                    nomeRotina = nome,
                    tempoMinutosRotina = tempoTotal
                )

                val idFinal: Int = if (idExistente == null) {
                    rotinaDAO.inserir(rotina).toInt()
                } else {
                    rotinaDAO.atualizar(rotina)
                    atividadeDAO.excluirPorRotina(idExistente)
                    idExistente
                }

                val rotinaFinal = rotina.copy(idRotina = idFinal)

                var proximoIdAtividade =
                    (atividadeDAO.buscarTodas()
                        .maxOfOrNull { it.idAtividade } ?: 0) + 1

                currentState.atividades.forEach { atividade ->
                    val atividadeSalva = atividade.copy(
                        idAtividade = proximoIdAtividade++,
                        idRotina = idFinal
                    )

                    atividadeDAO.inserir(atividadeSalva)
                }

                rotinaFinal
            }

            val indice = listaRotinas.indexOfFirst {
                it.idRotina == rotinaSalva.idRotina
            }

            if (indice >= 0) {
                listaRotinas[indice] = rotinaSalva
            } else {
                listaRotinas.add(rotinaSalva)
            }

            listaAtividades.removeAll {
                it.idRotina == rotinaSalva.idRotina
            }

            val atividadesSalvas = withContext(Dispatchers.IO) {
                BancoDadosProvider.db.atividadeDAO()
                    .buscarPorRotina(rotinaSalva.idRotina)
            }

            listaAtividades.addAll(atividadesSalvas)

            _uiState.update { it.copy(idRotinaEditando = rotinaSalva.idRotina) }

            rotinaSalva
        } catch (e: Exception) {
            Log.e("TelaCriacaoViewModel", "Erro ao salvar rotina", e)
            null
        }
    }

    fun carregarRotinaParaEditar(id: Int) {
        viewModelScope.launch {
            try {
                val resultado = withContext(Dispatchers.IO) {
                    val db = BancoDadosProvider.db

                    val rotina = db.rotinaDAO()
                        .buscarTodas()
                        .find { it.idRotina == id }

                    val atividadesExistentes = db.atividadeDAO()
                        .buscarPorRotina(id)

                    Pair(rotina, atividadesExistentes)
                }

                val rotina = resultado.first ?: return@launch

                _uiState.update {
                    it.copy(
                        idRotinaEditando = id,
                        nomeRotina = rotina.nomeRotina,
                        atividades = resultado.second
                    )
                }
            } catch (e: Exception) {
                Log.e("TelaCriacaoViewModel", "Erro ao carregar rotina", e)
            }
        }
    }

    fun excluirRotina(idRotina: Int) {
        viewModelScope.launch {
            try {
                excluirRotinaDoBanco(idRotina)
            } catch (e: Exception) {
                Log.e("TelaCriacaoViewModel", "Erro ao excluir rotina", e)
            }
        }
    }

    private suspend fun excluirRotinaDoBanco(idRotina: Int) {
        withContext(Dispatchers.IO) {
            val db = BancoDadosProvider.db

            db.atividadeDAO().excluirPorRotina(idRotina)

            val rotina = db.rotinaDAO()
                .buscarTodas()
                .find { it.idRotina == idRotina }

            if (rotina != null) {
                db.rotinaDAO().excluir(rotina)
            }
        }

        listaAtividades.removeAll { it.idRotina == idRotina }
        listaRotinas.removeAll { it.idRotina == idRotina }
    }
}