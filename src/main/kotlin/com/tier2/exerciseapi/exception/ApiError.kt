package com.tier2.exerciseapi.exception

data class ApiError (
    var status: Int? = null,
    var message: String? = null
)
