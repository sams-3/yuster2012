package com.kidshealth.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kidshealth.app.data.model.User
import com.kidshealth.app.data.model.UserType

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val email: String,
    val userType: String,
    val profileImageUrl: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val address: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

fun UserEntity.toUser(): User {
    return User(
        id = id,
        fullName = fullName,
        email = email,
        userType = UserType.valueOf(userType),
        profileImageUrl = profileImageUrl,
        phoneNumber = phoneNumber,
        dateOfBirth = dateOfBirth,
        address = address
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        fullName = fullName,
        email = email,
        userType = userType.name,
        profileImageUrl = profileImageUrl,
        phoneNumber = phoneNumber,
        dateOfBirth = dateOfBirth,
        address = address
    )
}