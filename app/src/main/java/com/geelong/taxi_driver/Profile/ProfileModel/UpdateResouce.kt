package com.geelong.taxi_driver.Profile.ProfileModel

data class UpdateResouce(
    val `data`: List<Any>,
    val error: Int,
    val image: String,
    val msg: String,
    val service: String,
    val success: String
)