package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.theme.Atividade
import com.example.myapplication.ui.theme.Rotina
import com.example.myapplication.ui.theme.listaAtividades
import com.example.myapplication.ui.theme.listaRotinas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.myapplication.ui.theme.listaAtividades as listaAtividadesGlobal
import com.example.myapplication.ui.theme.listaRotinas as listaRotinasGlobal

class TelaCriacaoViewModel : ViewModel() {

    // Estados do formulário
    var nomeRotina = androidx.compose.runtime.mutableStateOf("")
        private set

    var inicio = androidx.compose.runtime.mutableStateOf("08:00")
        private set

    var fim = androidx.compose.runtime.mutableStateOf("09:00")
        private set

    var nomeAtividade = androidx.compose.runtime.mutableStateOf("")
        private set

    var duracaoAtividade = androidx.compose.runtime.mutableStateOf("")
        private set


    // Lista reativa de atividades da rotina que está sendo criada
    val atividades = mutableStateListOf<Atividade>()


    // Próximo ID da rotina
    fun proximoIdRotina(): Int {
        return if (listaRotinasGlobal.isEmpty()) {
            1
        } else {
            listaRotinasGlobal.maxOf { it.idRotina } + 1
        }
    }


    // Alterações dos campos
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


    // Adiciona uma atividade à lista

    fun adicionarAtividade(): Boolean {
        val nome = nomeAtividade.value.trim()
        val duracao = duracaoAtividade.value.trim().toDoubleOrNull()

        if (nome.isBlank() || duracao == null || duracao <= 0) {
            return false
        }

        val novoId = (atividades.maxOfOrNull { it.idAtividade } ?: 0) + 1

        val novaAtividade = Atividade(
            idAtividade = novoId,
            idRotina = idRotinaEditando ?: proximoIdRotina(),
            nomeAtividade = nome,
            duracaoMinutos = duracao
        )

        // Adiciona à lista temporária da tela
        atividades.add(novaAtividade)

        // Limpa os campos
        nomeAtividade.value = ""
        duracaoAtividade.value = ""

        return true
    }
    /* fun adicionarAtividade(): Boolean {

        if (
            nomeAtividade.value.isBlank() ||
            duracaoAtividade.value.isBlank()
        ) {
            return false
        }

        val duracao = duracaoAtividade.value.toDoubleOrNull()

        if (duracao == null || duracao <= 0) {
            return false
        }

        val novoId = if (atividades.isEmpty()) {
            1
        } else {
            atividades.maxOf { it.idAtividade } + 1
        }

        val novoIdRotina = proximoIdRotina()

        val novaAtividade = Atividade(
            idAtividade = novoId,
            idRotina = novoIdRotina, // ID da rotina atual
            nomeAtividade = nomeAtividade.value,
            duracaoMinutos = duracao
        )

        listaAtividades.add(novaAtividade)

        BancoDadosProvider.db.atividadeDAO().inserir(novaAtividade)

        atividades.add(novaAtividade)

        // Limpa os campos
        nomeAtividade.value = ""
        duracaoAtividade.value = ""

        return true
    }*/


    // Remove uma atividade
    fun removerAtividade(id: Int) {
        atividades.removeAll {
            it.idAtividade == id
        }
    }


    // Salva a rotina inteira
    fun salvarRotina(): Rotina? {

        if (nomeRotina.value.isBlank()) {
            return null
        }

        val id = idRotinaEditando

        // =========================
        // EDITANDO
        // =========================
        if (id != null) {

            val rotina = listaRotinasGlobal.find {
                it.idRotina == id
            } ?: return null

            rotina.nomeRotina = nomeRotina.value

            rotina.tempoMinutosRotina =
                atividades.sumOf {
                    it.duracaoMinutos
                }

            listaAtividadesGlobal.removeAll {
                it.idRotina == id
            }

            atividades.forEach { atividade ->

                listaAtividadesGlobal.add(
                    atividade.copy(
                        idRotina = id
                    )
                )
            }

            return rotina
        }


        // =========================
        // CRIANDO NOVA
        // =========================

        val novoId = proximoIdRotina()

        val tempoTotal = atividades.sumOf {
            it.duracaoMinutos
        }

        val novaRotina = Rotina(
            idRotina = novoId,
            nomeRotina = nomeRotina.value,
            tempoMinutosRotina = tempoTotal
        )

        listaRotinasGlobal.add(novaRotina)

        atividades.forEach { atividade ->

            listaAtividadesGlobal.add(
                atividade.copy(
                    idRotina = novoId
                )
            )
        }

        return novaRotina
    }

    var idRotinaEditando: Int? = null
        private set

    fun carregarRotinaParaEditar(id: Int) {

        val rotina = listaRotinasGlobal.find {
            it.idRotina == id
        } ?: return

        idRotinaEditando = id

        nomeRotina.value = rotina.nomeRotina

        // Se você estiver salvando início e fim na Rotina,
        // podemos carregar esses valores aqui também.

        atividades.clear()

        atividades.addAll(
            listaAtividadesGlobal.filter {
                it.idRotina == id
            }
        )
    }

    fun excluirRotina(idRotina: Int) {
        viewModelScope.launch {
            try {
                excluirRotinaDoBanco(idRotina)
            } catch (e: Exception) {
                Log.e("RotiApp", "Erro ao excluir rotina", e)
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