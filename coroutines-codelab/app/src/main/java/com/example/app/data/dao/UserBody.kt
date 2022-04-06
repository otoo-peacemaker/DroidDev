package com.example.app

/**
 * Gets  information from the Backend API Retrofit service and updates the
 * [APIServices] and [NetworkStatus] [LiveData]. The Retrofit service returns a coroutine
 * Deferred, which we await to get the result of the transaction.
 */

data class UserBody(
    val email: String? = null,
    val password: String? = null,
    val confirm_password: String? = null,
    val reset_key: String? = null,
    var user_key: String? = null,
    var message: String? = null,
    val access: String? = null,
    var detail: String? = null,
    var token: String? = null

)

data class UserResponse(
    val token: Int? = null,
    var message: String? = null,
    var status: Int? = null,
    var detail: String? = null,
    val meta: String? = null,
    var data: UserBody? = null,
    val access: String? = null,
    val refresh: String? = null,
    val user: UserBody? = null
)