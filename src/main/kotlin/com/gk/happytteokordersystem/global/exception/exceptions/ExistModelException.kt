package com.gk.happytteokordersystem.global.exception.exceptions

data class ExistModelException(val modelName: String): RuntimeException(" $modelName is Duplicate")