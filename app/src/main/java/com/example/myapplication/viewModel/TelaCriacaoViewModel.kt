
package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Atividade
import com.example.myapplication.model.Rotina
import com.example.myapplication.model.listaAtividades
import com.example.myapplication.model.listaRotinas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TelaCriacaoViewModel : ViewModel() {

    var nomeRotina = mutableStateOf("")
        private set

    var inicio = mutableStateOf("08:00")
        private set

    var fim = mutableStateOf("09:00")
        private set

    var nomeAtividade = mutableStateOf("")
        private set

    var duracaoAtividade = mutableStateOf("")
        private set

    val atividades = mutableStateListOf<Atividade>()

    var idRotinaEditando: Int? = null
        private set

    fun proximoIdRotina(): Int {
        return (listaRotinas.maxOfOrNull { it.idRotina } ?: 0) + 1
    }

    fun alterarNomeRotina(valor: String) {
        nomeRotina.value = valor
    }

    fun alterarInicio(valor: String) {
        inicio.value = valor
    }

    fun alterarFim(valor: String) {
        fim.value = valor
    }

    fun alterarNomeAtividade(valor: String) {
        nomeAtividade.value = valor
    }

    fun alterarDuracaoAtividade(valor: String) {
        duracaoAtividade.value = valor.filter { it.isDigit() }
    }

    // Apenas adiciona a atividade à lista temporária.
    // O banco será atualizado ao salvar a rotina.
    fun adicionarAtividade(): Boolean {
        val nome = nomeAtividade.value.trim()
        val duracao = duracaoAtividade.value.toDoubleOrNull()

        if (nome.isBlank() || duracao == null || duracao <= 0) {
            return false
        }

        val novoId = (atividades.maxOfOrNull { it.idAtividade } ?: 0) + 1

        val novaAtividade = Atividade(
            idAtividade = novoId,
            idRotina = idRotinaEditando ?: 0,
            nomeAtividade = nome,
            duracaoMinutos = duracao
        )

        atividades.add(novaAtividade)

        nomeAtividade.value = ""
        duracaoAtividade.value = ""

        return true
    }

    fun removerAtividade(id: Int) {
        atividades.removeAll { it.idAtividade == id }
    }

    // Salva a rotina e suas atividades no Room.
    suspend fun salvarRotina(): Rotina? {
        val nome = nomeRotina.value.trim()

        if (nome.isBlank()) {
            return null
        }

        return try {
            val rotinaSalva = withContext(Dispatchers.IO) {
                val db = BancoDadosProvider.db
                val rotinaDAO = db.rotinaDAO()
                val atividadeDAO = db.atividadeDAO()

                val idExistente = idRotinaEditando

                val tempoTotal = atividades
                    .sumOf { it.duracaoMinutos }
                    .toInt()

                val rotina = Rotina(
                    idRotina = idExistente ?: 0,
                    nomeRotina = nome,
                    tempoMinutosRotina = tempoTotal
                )

                // Salva a rotina primeiro para obter o ID correto
                val idFinal: Int = if (idExistente == null) {
                    rotinaDAO.inserir(rotina).toInt()
                } else {
                    rotinaDAO.atualizar(rotina)
                    atividadeDAO.excluirPorRotina(idExistente)
                    idExistente
                }

                val rotinaFinal = rotina.copy(idRotina = idFinal)

                // Evita reutilizar IDs de atividades já existentes no banco.
                var proximoIdAtividade =
                    (atividadeDAO.buscarTodas()
                        .maxOfOrNull { it.idAtividade } ?: 0) + 1

                // Grava todas as atividades vinculadas à rotina correta.
                atividades.forEach { atividade ->
                    val atividadeSalva = atividade.copy(
                        idAtividade = proximoIdAtividade++,
                        idRotina = idFinal
                    )

                    atividadeDAO.inserir(atividadeSalva)
                }

                rotinaFinal
            }

            // Atualiza as listas em memória após salvar no banco.
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

            // Recarrega as atividades com os IDs que foram gravados no banco.
            val atividadesSalvas = withContext(Dispatchers.IO) {
                BancoDadosProvider.db.atividadeDAO()
                    .buscarPorRotina(rotinaSalva.idRotina)
            }

            listaAtividades.addAll(atividadesSalvas)

            idRotinaEditando = rotinaSalva.idRotina

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

                idRotinaEditando = id
                nomeRotina.value = rotina.nomeRotina

                atividades.clear()
                atividades.addAll(resultado.second)
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

    suspend fun excluirRotinaDoBanco(idRotina: Int) {
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
