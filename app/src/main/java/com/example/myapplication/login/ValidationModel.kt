package com.example.myapplication.login

import androidx.annotation.StringRes

data class ValidationModel(
    val isValid: Boolean,
    @StringRes val errorMessageId: Int?
) {
    companion object {
        fun valid() = ValidationModel(isValid = true, errorMessageId = null)
    }
}
