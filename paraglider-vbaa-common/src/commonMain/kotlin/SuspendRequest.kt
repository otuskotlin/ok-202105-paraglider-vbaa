package ru.paraglider.common.kmp

expect class SuspendRequest() {
    suspend fun request(): String
}