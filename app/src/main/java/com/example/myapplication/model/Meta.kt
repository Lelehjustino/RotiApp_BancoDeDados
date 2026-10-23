package com.example.myapplication.model

import androidx.compose.runtime.mutableStateListOf
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "metas")
data class Meta(
    @PrimaryKey(autoGenerate = true)
    val idMeta: Int = 0,
    val nomeMeta: String,
    val descricaoMeta: String,
    val concluida: Boolean = false
)
val listaMetas = mutableStateListOf<Meta>()