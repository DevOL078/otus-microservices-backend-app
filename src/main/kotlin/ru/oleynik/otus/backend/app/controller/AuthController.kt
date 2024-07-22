package ru.oleynik.otus.backend.app.controller

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import ru.oleynik.otus.backend.app.controller.mapper.UserMapper.toResponse
import ru.oleynik.otus.backend.app.controller.mapper.UserMapper.update
import ru.oleynik.otus.backend.app.controller.dto.UserRequest
import ru.oleynik.otus.backend.app.controller.dto.UserResponse
import ru.oleynik.otus.backend.app.domain.model.User
import ru.oleynik.otus.backend.app.service.UserService

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

    @PostMapping
    fun create(@RequestBody body: UserRequest): UserResponse {
        val user = User()
        user.update(body, bCryptPasswordEncoder)

        return userService.save(user).toResponse()
    }

    @GetMapping("/search")
    fun search(@RequestParam("login") login: String,
               @RequestParam("password") password: String): List<UserResponse> {
        return userService.searchByLogin(login)
            .filter { bCryptPasswordEncoder.matches(password, it.encryptedPassword) }
            .map { it.toResponse() }
    }

}