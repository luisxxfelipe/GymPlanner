package com.dispositivosmoveis.gymplanner.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercicios")
class Exercicio(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val series: Int,
    val repeticoes: Int,
    val treinoId: Long, // Chave estrangeira para o treino
    val createdAt: Long = System.currentTimeMillis()
)