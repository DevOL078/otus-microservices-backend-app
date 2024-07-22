package ru.oleynik.otus.backend.app.config.security

import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.interfaces.RSAPublicKey

@ConfigurationProperties(prefix = "auth.jwt")
class JwtConfig(
    val publicKey: RSAPublicKey,
)