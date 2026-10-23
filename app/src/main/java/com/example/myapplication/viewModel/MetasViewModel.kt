package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Meta
import com.example.myapplication.model.listaMetas
import com.example.myapplication.model.registrarMetaNoHistorico
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MetasViewModel : ViewModel() {

    // Lista reativa utilizada pela tela
    val metas = listaMetas

    init {
        carregarMetas()
    }

    // Carrega as metas do banco
    fun carregarMetas() {
        viewModelScope.launch {
            try {
                val resultado = withContext(Dispatchers.IO) {
                    BancoDadosProvider.db
                        .metaDAO()
                        .buscarTodas()
                }

                metas.clear()
                metas.addAll(resultado)

            } catch (e: Exception) {
                Log.e("MetasViewModel", "Erro ao carregar metas", e)
            }
        }
    }

    // Adiciona uma meta e recebe o ID gerado pelo Room

    fun adicionarMeta(nome: String, descricao: String) {
        if (nome.isBlank()) return

        viewModelScope.launch {
            try {
                val meta = Meta(
                    nomeMeta = nome.trim(),
                    descricaoMeta = descricao.trim(),
                    concluida = false
                )

                withContext(Dispatchers.IO) {
                    BancoDadosProvider.db
                        .metaDAO()
                        .inserir(meta)
                }

                carregarMetas()

            } catch (e: Exception) {
                Log.e("MetasViewModel", "Erro ao adicionar meta", e)
            }
        }
    }


    // Marca ou desmarca uma meta
    fun alternarMeta(meta: Meta) {
        viewModelScope.launch {
            try {
                val atualizada = meta.copy(
                    concluida = !meta.concluida
                )

                withContext(Dispatchers.IO) {
                    BancoDadosProvider.db
                        .metaDAO()
                        .atualizar(atualizada)
                }

                val indice = metas.indexOfFirst {
                    it.idMeta == meta.idMeta
                }

                if (indice != -1) {
                    metas[indice] = atualizada
                }

                if (atualizada.concluida) {
                    registrarMetaNoHistorico(
                        atualizada.nomeMeta
                    )
                }

            } catch (e: Exception) {
                Log.e("MetasViewModel", "Erro ao atualizar meta", e)
            }
        }
    }

    // Exclui a meta do banco e da lista
    fun removerMeta(meta: Meta) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    BancoDadosProvider.db
                        .metaDAO()
                        .excluir(meta)
                }

                metas.removeAll {
                    it.idMeta == meta.idMeta
                }

            } catch (e: Exception) {
                Log.e("MetasViewModel", "Erro ao excluir meta", e)
            }
        }
    }

    // Atualiza o nome e a descrição de uma meta
    fun atualizarMeta(
        idMeta: Int,
        nome: String,
        descricao: String
    ) {
        if (nome.isBlank()) return

        viewModelScope.launch {
            try {
                val indice = metas.indexOfFirst {
                    it.idMeta == idMeta
                }

                if (indice == -1) return@launch

                val atualizada = metas[indice].copy(
                    nomeMeta = nome.trim(),
                    descricaoMeta = descricao.trim()
                )

                withContext(Dispatchers.IO) {
                    BancoDadosProvider.db
                        .metaDAO()
                        .atualizar(atualizada)
                }

                metas[indice] = atualizada

            } catch (e: Exception) {
                Log.e("MetasViewModel", "Erro ao editar meta", e)
            }
        }
    }

    fun buscarMeta(idMeta: Int): Meta? {
        return metas.find {
            it.idMeta == idMeta
        }
    }
}