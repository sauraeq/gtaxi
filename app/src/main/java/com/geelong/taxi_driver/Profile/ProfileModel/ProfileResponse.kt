package com.geelong.taxi_driver.Profile.ProfileModel

data class ProfileResponse(
    val `data`: Data,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)