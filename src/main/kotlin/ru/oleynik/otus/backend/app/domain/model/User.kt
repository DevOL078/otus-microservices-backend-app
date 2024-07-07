package ru.oleynik.otus.backend.app.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users-seq")
    @SequenceGenerator(name = "users-seq", sequenceName = "users_id_seq", allocationSize = 1)
    var id: Long? = null,
    var username: String = "",
    var encryptedPassword: String = "",
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null
)