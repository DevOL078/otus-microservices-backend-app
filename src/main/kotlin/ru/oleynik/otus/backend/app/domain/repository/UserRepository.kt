package ru.oleynik.otus.backend.app.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.oleynik.otus.backend.app.domain.model.User

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): List<User>

}