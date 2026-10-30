package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Atividade
import com.example.myapplication.model.Rotina
import com.example.myapplication.data.repository.RotinaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RotinaViewModel(
    private val repository: RotinaRepository = RotinaRepository()
) : ViewModel() {

    var rotina by mutableStateOf<Rotina?>(null)
        private set

    val atividadesDaRotina = mutableStateListOf<Atividade>()
    val atividadesConcluidas = mutableStateMapOf<Int, Boolean>()

    private val _carregando = MutableStateFlow(true)
    val carregando: StateFlow<Boolean> = _carregando.asStateFlow()

    private val _erroCarregamento = MutableStateFlow(false)
    val erroCarregamento: StateFlow<Boolean> = _erroCarregamento.asStateFlow()

    fun carregarDados(idRotina: Int?) {
        viewModelScope.launch {
            rotina = null
            atividadesDaRotina.clear()
            atividadesConcluidas.clear()
            _carregando.value = true
            _erroCarregamento.value = false

            if (idRotina == null) {
                _carregando.value = false
                return@launch
            }

            try {
                val (rotinaEncontrada, atividades) = repository.buscarRotinaEAtividades(idRotina)
                rotina = rotinaEncontrada
                atividadesDaRotina.addAll(atividades)
            } catch (e: Exception) {
                _erroCarregamento.value = true
            } finally {
                _carregando.value = false
            }
        }
    }

    fun alternarConclusao(idAtividade: Int) {
        val atual = atividadesConcluidas[idAtividade] ?: false
        atividadesConcluidas[idAtividade] = !atual
    }

    fun excluirAtividade(atividade: Atividade) {
        viewModelScope.launch {
            try {
                repository.excluirAtividade(atividade)
                atividadesDaRotina.removeAll { it.idAtividade == atividade.idAtividade }
                atividadesConcluidas.remove(atividade.idAtividade)
            } catch (e: Exception) {
                _erroCarregamento.value = true
            }
        }
    }
}