package com.geelong.taxi_driver.Setting.PrivacyAndPolicy

data class PrivacyAndPolicyResource(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)