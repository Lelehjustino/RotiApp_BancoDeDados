package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Meta
import com.example.myapplication.model.listaMetas
import com.example.myapplication.viewmodel.uistate.TelaMetasUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MetasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TelaMetasUiState(metas = listaMetas.toList()))
    val uiState: StateFlow<TelaMetasUiState> = _uiState.asStateFlow()

    fun atualizarDialogoNovaMeta(mostrar: Boolean) {
        _uiState.update { it.copy(mostrarDialogoNovaMeta = mostrar) }
    }

    fun selecionarMeta(meta: Meta?) {
        _uiState.update { it.copy(metaSelecionada = meta) }
    }

    fun adicionarMeta(nome: String, descricao: String) {
        val novaMeta = Meta(
            idMeta = (listaMetas.maxOfOrNull { it.idMeta } ?: 0) + 1,
            nomeMeta = nome,
            descricaoMeta = descricao,
            concluida = false
        )
        listaMetas.add(novaMeta)
        _uiState.update { it.copy(metas = listaMetas.toList()) }
    }

    fun alternarMeta(meta: Meta) {
        val index = listaMetas.indexOfFirst { it.idMeta == meta.idMeta }
        if (index >= 0) {
            listaMetas[index] = meta.copy(concluida = !meta.concluida)
            _uiState.update { it.copy(metas = listaMetas.toList()) }
        }
    }

    fun removerMeta(meta: Meta) {
        listaMetas.removeIf { it.idMeta == meta.idMeta }
        _uiState.update { it.copy(metas = listaMetas.toList()) }
    }
}