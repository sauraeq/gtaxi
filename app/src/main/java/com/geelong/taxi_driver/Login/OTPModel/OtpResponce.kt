package com.geelong.taxi_driver.Login.OTPModel

data class OtpResponce(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)