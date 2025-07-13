package com.dispositivosmoveis.gymplanner.repository

import com.dispositivosmoveis.gymplanner.dao.UserDao
import com.dispositivosmoveis.gymplanner.entities.User
import kotlinx.coroutines.flow.firstOrNull

class AuthRepository (private val userDao: UserDao) {

    suspend fun login (username: String, password: String): User? {
        return userDao.login(username, password)
    }

    suspend fun registerUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id).firstOrNull()
    }
}