package com.dispositivosmoveis.gymplanner.repository

import com.dispositivosmoveis.gymplanner.data.Exercicio
import com.dispositivosmoveis.gymplanner.data.ExercicioDao
import kotlinx.coroutines.flow.Flow

/**
 * Essa parte será responsavel para centralizar o acesso aos dados dos exercicios
 * Ele basicamente faz a comunicação entre a UI e a ROOM (pelo ExercicioDao) de forma mais limpa
 */

class ExercicioRepository(private val exercicioDao: ExercicioDao) {

    fun listarExerciciosPorTreino(treinoId: Long): Flow<List<Exercicio>>{
        return exercicioDao.getExerciciosByTreinoId(treinoId)
    }

    suspend fun inserirExercicio(exercicio: Exercicio){
        exercicioDao.insert(exercicio)
    }

    suspend fun atualizarExercicio(exercicio: Exercicio){
        exercicioDao.update(exercicio)
    }

    suspend fun deletarExercicio(exercicio: Exercicio){
        exercicioDao.delete(exercicio)
    }
}