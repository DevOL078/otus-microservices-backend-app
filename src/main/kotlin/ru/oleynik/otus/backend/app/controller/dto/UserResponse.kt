package ru.oleynik.otus.backend.app.controller.dto

data class UserResponse(
    val id: Long,
    var username: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null
)