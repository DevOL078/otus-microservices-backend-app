package ru.oleynik.otus.backend.app.controller

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import ru.oleynik.otus.backend.app.controller.dto.UserRequest
import ru.oleynik.otus.backend.app.controller.dto.UserResponse
import ru.oleynik.otus.backend.app.domain.model.User

object UserMapper {
    fun User.toResponse(): UserResponse = UserResponse(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        username = this.username,
        email = this.email,
        phone = this.phone
    )

    fun User.update(request: UserRequest, bCryptPasswordEncoder: BCryptPasswordEncoder) {
        this.username = request.username
        this.encryptedPassword = bCryptPasswordEncoder.encode(request.password)
        this.firstName = request.firstName
        this.lastName = request.lastName
        this.phone = request.phone
        this.email = request.email
    }

}