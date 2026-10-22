package com.example.myapplication.ui.theme

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "perfil")
data class Perfil(
    @PrimaryKey
    val id: Int = 1,

    val nome: String = "",
    val email: String = ""
)
