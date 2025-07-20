package org.example.blink.common.response

import java.time.LocalDateTime

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val errorCode: String? = null,
    val errors: Map<String, String>? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun <T> success(data: T? = null, message: String = "성공"): ApiResponse<T> {
            return ApiResponse(success = true, message = message, data = data)
        }

        fun <T> error(errorCode: String, message: String, errors: Map<String, String>? = null): ApiResponse<T> {
            return ApiResponse(success = false, message = message, errorCode = errorCode, errors = errors)
        }
    }
} 