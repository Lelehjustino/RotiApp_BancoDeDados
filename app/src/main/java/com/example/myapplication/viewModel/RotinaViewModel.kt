
package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.RotinaRepository
import com.example.myapplication.model.Atividade
import com.example.myapplication.viewmodel.uistate.RotinaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RotinaViewModel(
    private val repository: RotinaRepository = RotinaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RotinaUiState())
    val uiState: StateFlow<RotinaUiState> = _uiState.asStateFlow()

    fun carregarDados(idRotina: Int?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    rotina = null,
                    atividadesDaRotina = emptyList(),
                    atividadesConcluidas = emptySet(),
                    carregando = true,
                    erroCarregamento = false
                )
            }

            if (idRotina == null) {
                _uiState.update {
                    it.copy(carregando = false)
                }
                return@launch
            }

            try {
                val (rotinaEncontrada, atividades) =
                    repository.buscarRotinaEAtividades(idRotina)

                _uiState.update {
                    it.copy(
                        rotina = rotinaEncontrada,
                        atividadesDaRotina = atividades,
                        carregando = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        carregando = false,
                        erroCarregamento = true
                    )
                }
            }
        }
    }

    fun alternarConclusao(idAtividade: Int) {
        _uiState.update { estado ->
            val concluidas = estado.atividadesConcluidas.toMutableSet()

            if (idAtividade in concluidas) {
                concluidas.remove(idAtividade)
            } else {
                concluidas.add(idAtividade)
            }

            estado.copy(atividadesConcluidas = concluidas)
        }
    }

    fun excluirAtividade(atividade: Atividade) {
        viewModelScope.launch {
            try {
                repository.excluirAtividade(atividade)

                _uiState.update { estado ->
                    estado.copy(
                        atividadesDaRotina = estado.atividadesDaRotina.filter {
                            it.idAtividade != atividade.idAtividade
                        },
                        atividadesConcluidas = estado.atividadesConcluidas -
                                atividade.idAtividade
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(erroCarregamento = true)
                }
            }
        }
    }
}
