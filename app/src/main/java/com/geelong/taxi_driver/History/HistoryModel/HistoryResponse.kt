package com.geelong.taxi_driver.History.HistoryModel

data class HistoryResponse(
    val `data`: List<Data>,
    val dataall: List<Data.Dataall>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
) {
    data class Data(
        val amount: String,
        val cancel: String,
        val created_date: String,
        val cupon_code: String,
        val driver_id: String,
        val drop_address: String,
        val drop_latitude: String,
        val drop_longitude: String,
        val id: String,
        val otp: Any,
        val pickup_address: String,
        val pickup_latitude: String,
        val pickup_longitude: String,
        val status: String,
        val time: String,
        val uid: String,
        val distance:String,
        val user_name: String,
        val user_photo: String
    ) {
        data class Dataall(
            val amount: String,
            val time: String,
            val total_ride: String
        )
    }
}