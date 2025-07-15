package com.dispositivosmoveis.gymplanner.repository

import com.dispositivosmoveis.gymplanner.entities.Treino
import com.dispositivosmoveis.gymplanner.dao.TreinoDao
import kotlinx.coroutines.flow.Flow

/**
 * Essa parte será responsavel para centralizar o acesso aos dados do treino
 * Ele basicamente faz a comunicação entre a UI e a ROOM (pelo TreinoDao) de forma mais limpa
 */

class TreinoRepository(private val treinoDao: TreinoDao){

    fun buscarTreinosPorUsuario(usuarioId: Long): Flow<List<Treino>> {
        return treinoDao.getAllTreinos(usuarioId)
    }

    suspend fun inserirTreino(treino: Treino) {
        treinoDao.insert(treino)
    }

    suspend fun atualizarTreino(treino: Treino) {
        treinoDao.update(treino)
    }

    suspend fun deletarTreino(treino: Treino) {
        treinoDao.delete(treino)
    }

    suspend fun buscarTreinoPorId(treinoId: Long): Treino? {
        return treinoDao.getTreinoById(treinoId)
    }
}
