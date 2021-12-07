package ru.kotlin.paraglider.vbaa.be.common.models

enum class CommonUserGroups {
    USER,
    //learning to fly, has applied to at least 1 school (later can view materials of school and so on)
    STUDENT,
    //can apply to work as instructor
    PILOT,
    //head of school//school can also be with only 1 instructor
    SCHOOL_HEAD,
    //instructor can create services and sell goods
    INSTRUCTOR,
    //stuff to help organising school activities
    SCHOOL_STUFF,
    //ROOT
    APP_MODERATOR,
    TEST,
    BLOCKED_USER
}
