package ru.wolfram.common.presentation

import android.util.Log
import kotlin.reflect.KClass

private const val COMPONENT_CREATION_TAG = "COMPONENT_CREATION"

fun <T : Any> logCreation(kClass: KClass<T>, tag: String = COMPONENT_CREATION_TAG) {
    Log.d(tag, "${kClass.simpleName} is created")
}