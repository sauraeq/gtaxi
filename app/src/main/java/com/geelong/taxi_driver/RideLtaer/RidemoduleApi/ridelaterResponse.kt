package com.geelong.taxi_driver.RideLtaer.RidemoduleApi

data class ridelaterResponse(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)
{

    data class Data(
        val amount: String,
        val distance: String,
        val driver_id: String,
        val drop_address: String,
        val drop_latitude: String,
        val drop_longitude: String,
        val id: String,
        val otp: String,
        val otp_status: String,
        val pickup_address: String,
        val pickup_latitude: String,
        val pickup_longitude: String,
        val remark: String,
        val status: String,
        val time: String,
        val uid: String,
        val user_name: String,
        val user_photo: String
    )
}