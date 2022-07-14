package com.geelong.taxi_driver.Notification.NotificationModel

data class NotificationResponse(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)
{

    data class Data(
        val created_date: String,
        val description: String,
        val id: String,
        val title: String,
        val type: String,
        val user_driver_id: String
    )
}