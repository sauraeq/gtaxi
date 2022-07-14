package com.geelong.taxi_driver.fragment

data class LocationUpdate(
    val `data`: Boolean,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)