package com.geelong.taxi_driver.fragment.Complete_Model

data class completeResponse(
    val `data`: List<Any>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)