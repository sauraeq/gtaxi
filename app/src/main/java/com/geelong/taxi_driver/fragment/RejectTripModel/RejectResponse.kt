package com.geelong.taxi_driver.fragment.RejectTripModel

data class RejectResponse(
    val `data`: Boolean,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)