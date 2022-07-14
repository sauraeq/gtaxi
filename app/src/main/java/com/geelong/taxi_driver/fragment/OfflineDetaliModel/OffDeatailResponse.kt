package com.geelong.taxi_driver.fragment.OfflineDetaliModel

data class OffDeatailResponse(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)