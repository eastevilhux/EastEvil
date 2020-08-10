package com.god.uikit.entity

enum class Status (
    val status: Int
){
    STATUS_NOTING(0x00),
    STATUS_START(0x01),
    STATUS_END(0x02),
    STATUS_SHOW(0x03),
    STATUS_DISMISS(0x04);
}
