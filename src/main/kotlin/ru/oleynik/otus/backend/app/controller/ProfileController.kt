package ru.oleynik.otus.backend.app.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import ru.oleynik.otus.backend.app.config.security.UserInfo
import ru.oleynik.otus.backend.app.controller.UserMapper.toResponse
import ru.oleynik.otus.backend.app.controller.UserMapper.update
import ru.oleynik.otus.backend.app.controller.dto.UserRequest
import ru.oleynik.otus.backend.app.controller.dto.UserResponse
import ru.oleynik.otus.backend.app.service.UserService

@RestController
@RequestMapping("/profile")
class ProfileController(
    private val userService: UserService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

    @GetMapping("/{userId}")
    fun getById(@PathVariable userId: Long): UserResponse {
        checkAccess(userId)
        return userService.getById(userId).toResponse()
    }

    @DeleteMapping("/{userId}")
    fun delete(@PathVariable userId: Long): ResponseEntity<Any> {
        userService.delete(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PutMapping("/{userId}")
    fun edit(@PathVariable userId: Long,
             @RequestBody body: UserRequest): ResponseEntity<Any> {
        val user = userService.getById(userId)
        user.update(body, bCryptPasswordEncoder)

        val saved = userService.save(user)

        return ResponseEntity.ok()
            .build()
    }

    private fun checkAccess(targetUserId: Long) {
        val authInfo = SecurityContextHolder.getContext()
            .authentication.principal as UserInfo

        if (authInfo.userId != targetUserId) {
            throw AccessDeniedException("access denied by token")
        }
    }

}