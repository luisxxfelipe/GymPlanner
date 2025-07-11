package com.dispositivosmoveis.gymplanner.repository

import com.dispositivosmoveis.gymplanner.data.Treino
import com.dispositivosmoveis.gymplanner.data.TreinoDao
import kotlinx.coroutines.flow.Flow

/**
 * Essa parte será responsavel para centralizar o acesso aos dados do treino
 * Ele basicamente faz a comunicação entre a UI e a ROOM (pelo TreinoDao) de forma mais limpa
 */

class TreinoRepository(private val treinoDao: TreinoDao){
    val listarTreinos : Flow<List<Treino>> = treinoDao.getAllTreinos()

    suspend fun inserirTreino(treino: Treino){
        treinoDao.insert(treino)
    }

    suspend fun atualizarTreino(treino: Treino){
        treinoDao.update(treino)
    }

    suspend fun deletarTreino(treino: Treino){
        treinoDao.delete(treino)
    }
}
