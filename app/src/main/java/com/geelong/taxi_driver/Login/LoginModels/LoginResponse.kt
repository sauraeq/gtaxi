package com.geelong.taxi_driver.Login.LoginModels

data class LoginResponse(
    val `data`: Data,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)
