package ru.oleynik.otus.backend.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import ru.oleynik.otus.backend.app.config.security.JwtConfig

@SpringBootApplication
@ComponentScan(basePackages = ["ru.oleynik.otus.backend.app"])
@EnableConfigurationProperties(
    JwtConfig::class,
)
class BackendAppApplication

fun main(args: Array<String>) {
    runApplication<BackendAppApplication>(*args)
}
