package ru.wolfram.auth.validation

import jakarta.validation.Constraint
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [])
@Size(min = 1, max = 32)
@Pattern(regexp = "^\\S+$")
annotation class ValidUsername(
    val message: String = "Invalid username",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
