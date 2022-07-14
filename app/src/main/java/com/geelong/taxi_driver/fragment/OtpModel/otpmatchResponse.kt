package com.geelong.taxi_driver.fragment.OtpModel

data class otpmatchResponse(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)