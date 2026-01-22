package ru.wolfram.administration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdministrationApplication

fun main(args: Array<String>) {
    runApplication<AdministrationApplication>(*args)
}
