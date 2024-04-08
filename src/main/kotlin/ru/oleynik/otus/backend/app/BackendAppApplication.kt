package ru.oleynik.otus.backend.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["ru.oleynik.otus.backend.app"])
class BackendAppApplication

fun main(args: Array<String>) {
    runApplication<BackendAppApplication>(*args)
}
