package com.kidshealth.app.data.supabase.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.kidshealth.app.data.model.User
import com.kidshealth.app.data.model.UserType

@Serializable
data class UserDto(
    val id: String,
    @SerialName("full_name")
    val fullName: String,
    val email: String,
    @SerialName("user_type")
    val userType: String,
    @SerialName("profile_image_url")
    val profileImageUrl: String = "",
    @SerialName("phone_number")
    val phoneNumber: String = "",
    @SerialName("date_of_birth")
    val dateOfBirth: String = "",
    val address: String = "",
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

fun UserDto.toUser(): User {
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

fun User.toDto(): UserDto {
    return UserDto(
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