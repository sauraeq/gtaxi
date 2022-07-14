package com.geelong.taxi_driver.Help_and_Support.HelpSupportModel

data class HelpSupportResponse(
    val `data`: Int,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)