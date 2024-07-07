package ru.oleynik.otus.backend.app.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.oleynik.otus.backend.app.controller.dto.UserRequest
import ru.oleynik.otus.backend.app.controller.dto.UserResponse
import ru.oleynik.otus.backend.app.domain.model.User
import ru.oleynik.otus.backend.app.service.UserService

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestBody body: UserRequest): UserResponse {
        val user = User()
        editUser(body, user)

        val saved = userService.save(user)

        return map(saved)
    }

    @GetMapping("/{userId}")
    fun getById(@PathVariable userId: Long): UserResponse {
        return map(userService.getById(userId))
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
        editUser(body, user)

        val saved = userService.save(user)

        return ResponseEntity.ok()
            .build()
    }

    private fun editUser(request: UserRequest, entity: User) {
        entity.username = request.username
        entity.firstName = request.firstName
        entity.lastName = request.lastName
        entity.phone = request.phone
        entity.email = request.email
    }

    private fun map(user: User): UserResponse {
        return UserResponse(
            id = user.id!!,
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.username,
            email = user.email,
            phone = user.phone
        )
    }

}