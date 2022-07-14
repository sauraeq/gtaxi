package com.geelong.taxi_driver.fragment.OnlineDetailModel

data class OnlineResponse(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)