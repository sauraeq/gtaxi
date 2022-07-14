package com.geelong.taxi_driver.Setting.TermAndCondition

data class TermAndConditionResource(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)