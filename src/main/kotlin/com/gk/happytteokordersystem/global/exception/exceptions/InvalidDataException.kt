package com.gk.happytteokordersystem.global.exception.exceptions

data class InvalidDataException(val data: String): RuntimeException(
    "invalid $data"
)