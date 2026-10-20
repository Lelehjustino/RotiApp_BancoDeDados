
package com.example.myapplication.ui.theme

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.compose.runtime.mutableStateListOf

@Entity(tableName = "metas")
data class Meta(
    @PrimaryKey
    var idMeta: Int = 0,
    var nomeMeta: String = "",
    var descricaoMeta: String = "",
    var concluida: Boolean = false
)

val listaMetas = mutableStateListOf<Meta>()
