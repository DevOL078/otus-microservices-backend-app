package ru.oleynik.otus.backend.app.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import ru.oleynik.otus.backend.app.controller.dto.UserRequest
import ru.oleynik.otus.backend.app.controller.dto.UserResponse
import ru.oleynik.otus.backend.app.controller.mapper.UserMapper.toResponse
import ru.oleynik.otus.backend.app.controller.mapper.UserMapper.update
import ru.oleynik.otus.backend.app.domain.model.User
import ru.oleynik.otus.backend.app.service.UserService

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

    @PostMapping
    fun create(@RequestBody body: UserRequest): ResponseEntity<UserResponse> {
        val existed = userService.searchByLogin(body.username)

        if (existed != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

        val user = User()
        user.update(body, bCryptPasswordEncoder)

        return ResponseEntity.ok(userService.save(user).toResponse())
    }

    @GetMapping("/search")
    fun search(@RequestParam("login") login: String,
               @RequestParam("password") password: String): ResponseEntity<UserResponse> {
        return userService.searchByLogin(login)
            ?.takeIf { bCryptPasswordEncoder.matches(password, it.encryptedPassword) }
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

}