package com.kidshealth.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val userType: UserType = UserType.PATIENT,
    val profileImageUrl: String = "",
    val phoneNumber: String = "",
    val dateOfBirth: String = "",
    val address: String = ""
) : Parcelable

enum class UserType {
    PATIENT, DOCTOR, ADMIN
}