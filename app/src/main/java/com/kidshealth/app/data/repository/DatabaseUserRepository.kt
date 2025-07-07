package com.kidshealth.app.data.repository

import com.kidshealth.app.data.database.dao.UserDao
import com.kidshealth.app.data.database.entities.toEntity
import com.kidshealth.app.data.database.entities.toUser
import com.kidshealth.app.data.model.User
import com.kidshealth.app.data.model.UserType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseUserRepository(
    private val userDao: UserDao
) {
    
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toUser() }
        }
    }
    
    fun getUsersByType(userType: UserType): Flow<List<User>> {
        return userDao.getUsersByType(userType.name).map { entities ->
            entities.map { it.toUser() }
        }
    }
    
    suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)?.toUser()
    }
    
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toUser()
    }
    
    suspend fun saveUser(user: User) {
        userDao.insertUser(user.toEntity())
    }
    
    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toEntity())
    }
    
    suspend fun deleteUser(userId: String) {
        userDao.deleteUserById(userId)
    }
    
    suspend fun authenticateUser(email: String, password: String): User? {
        // In a real app, you would hash the password and compare
        // For now, we'll just check if the user exists
        return getUserByEmail(email)
    }
}