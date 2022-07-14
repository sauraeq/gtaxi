package com.geelong.taxi_driver.navigation_drawer.Noti_countModel

data class NotificationCountResponse(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)