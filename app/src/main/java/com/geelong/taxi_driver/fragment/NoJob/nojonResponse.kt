package com.geelong.taxi_driver.fragment.NoJob

data class nojonResponse(
    val `data`: Boolean,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)