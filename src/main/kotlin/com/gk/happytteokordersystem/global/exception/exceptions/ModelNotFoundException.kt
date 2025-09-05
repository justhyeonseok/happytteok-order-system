package com.gk.happytteokordersystem.global.exception.exceptions

data class ModelNotFoundException(val modelName: String) : RuntimeException(
    "Model $modelName not found"
)