package com.dispositivosmoveis.gymplanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercicios")
class Exercicio(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val treinoId: Int // Chave estrangeira para o treino
)