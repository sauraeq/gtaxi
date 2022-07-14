package com.geelong.taxi_driver.Bank

data class
GetClientResponse(
    val base_url: String,
    val `data`: List<Data>,
    val msg: String,
    val success: String
) {
    data class Data(
        val accessToken: Any,
        val added_on: String,
        val country: Any,
        val dob: String,
        val email: String,
        val first_name: String,
        val gender: String,
        val id: String,
        val is_login: String,
        val lab_id: String,
        val last_name: String,
        val mobile: String,
        val otp: Any,
        val password: String,
        val programme_code: String,
        val status: String,
        val title: String,
        val user_type: String
    )
}