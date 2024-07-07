package ru.oleynik.otus.backend.app.service

import org.springframework.stereotype.Service
import ru.oleynik.otus.backend.app.domain.model.User
import ru.oleynik.otus.backend.app.domain.repository.UserRepository

@Service
class UserService(
    private val repository: UserRepository,
) {

    fun getById(id: Long): User = repository.findById(id)
        .orElseThrow { NoSuchElementException("user with id $id not found") }

    fun save(user: User): User = repository.save(user)

    fun delete(id: Long) {
        repository.deleteById(id)
    }

    fun searchByLogin(login: String): List<User> {
        return repository.findByUsername(login)
    }

}