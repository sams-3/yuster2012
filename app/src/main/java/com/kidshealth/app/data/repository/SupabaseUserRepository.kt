package com.kidshealth.app.data.repository

import com.kidshealth.app.data.model.User
import com.kidshealth.app.data.model.UserType
import com.kidshealth.app.data.supabase.SupabaseClient
import com.kidshealth.app.data.supabase.dto.UserDto
import com.kidshealth.app.data.supabase.dto.toDto
import com.kidshealth.app.data.supabase.dto.toUser
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SupabaseUserRepository {
    
    private val client = SupabaseClient.client
    
    fun getAllUsers(): Flow<List<User>> = flow {
        try {
            val response = client.from("users").select().decodeList<UserDto>()
            emit(response.map { it.toUser() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    fun getUsersByType(userType: UserType): Flow<List<User>> = flow {
        try {
            val response = client.from("users")
                .select()
                .select(Columns.list("*")) {
                    eq("user_type", userType.name)
                }
                .decodeList<UserDto>()
            emit(response.map { it.toUser() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    suspend fun getUserById(userId: String): User? {
        return try {
            val response = client.from("users")
                .select()
                .select(Columns.list("*")) {
                    eq("id", userId)
                }
                .decodeSingleOrNull<UserDto>()
            response?.toUser()
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun getUserByEmail(email: String): User? {
        return try {
            val response = client.from("users")
                .select()
                .select(Columns.list("*")) {
                    eq("email", email)
                }
                .decodeSingleOrNull<UserDto>()
            response?.toUser()
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun saveUser(user: User) {
        try {
            client.from("users").insert(user.toDto())
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun updateUser(user: User) {
        try {
            client.from("users")
                .update(user.toDto()) {
                    eq("id", user.id)
                }
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun deleteUser(userId: String) {
        try {
            client.from("users")
                .delete {
                    eq("id", userId)
                }
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun signUp(email: String, password: String): User? {
        return try {
            val result = client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            result.user?.let { authUser ->
                val user = User(
                    id = authUser.id,
                    email = email,
                    fullName = "",
                    userType = UserType.PATIENT
                )
                saveUser(user)
                user
            }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun signIn(email: String, password: String): User? {
        return try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            getUserByEmail(email)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun signOut() {
        try {
            client.auth.signOut()
        } catch (e: Exception) {
            // Handle error
        }
    }
}