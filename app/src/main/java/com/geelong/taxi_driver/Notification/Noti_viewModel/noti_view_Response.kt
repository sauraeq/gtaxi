package com.geelong.taxi_driver.Notification.Noti_viewModel

data class noti_view_Response(
    val `data`: Boolean,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)