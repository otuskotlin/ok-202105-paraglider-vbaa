package ru.kotlin.paraglider.vbaa.be.common.models

//TODO ПЕРЕНЕСТИ ЧАСТЬ РАЗРЕШЕНИЙ В ДРУГИЕ МИКРОСЕРВИСЫ (CRUD)
enum class SchoolUserPermissions {

    CREATE_SCHOOL,
    CREATE_TRIP,
    CREATE_SERVICE,

    READ_SCHOOL_OWN,
    READ_SCHOOL_PUBLIC,

    READ_TRIP_OWN,
    READ_TRIP_PUBLIC,
    //спец предложения в рамках школы (только для курсантов школы)
    READ_TRIP_PRIVATE,

    READ_SERVICE_OWN,
    READ_SERVICE_PUBLIC,
    //спец предложения сервисов школы (только для курсантов школы)
    READ_SERVICE_PRIVATE,

    UPDATE_SCHOOL,
    UPDATE_TRIP,
    UPDATE_SERVICE,

    DELETE_SCHOOL,
    DELETE_TRIP,
    DELETE_SERVICE,

    SEARCH_SCHOOL_OWN,
    SEARCH_SCHOOL_PUBLIC,

    SEARCH_TRIP_OWN,
    SEARCH_TRIP_PUBLIC,
    SEARCH_TRIP_PRIVATE,

    SEARCH_SERVICE_OWN,
    SEARCH_SERVICE_PUBLIC,
    SEARCH_SERVICE_PRIVATE,

    SEARCH_INSTRUCTORS_PUBLIC
}
