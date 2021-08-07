package ru.paraglider.common.kmp

import kotlinx.coroutines.delay

actual class SuspendRequest actual constructor(){
    actual suspend fun request(): String {
        delay(1000)
        return "Suspend JS"
    }
}