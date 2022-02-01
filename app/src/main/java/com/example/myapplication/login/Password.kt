package com.example.myapplication.login

import com.example.myapplication.Validation


@JvmInline
value class Password(val value: String) : Validation {

    override fun checkValidation(): ValidationModel {

        return when {
            value.isEmpty() -> ValidationModel(
                isValid = false,
                errorMessageId = StringRes.password_empty_error
            )

            value.length < MIN_PASSWORD_CHAR_COUNT -> ValidationModel(
                isValid = false,
                errorMessageId = StringRes.password_char_error
            )

            else -> ValidationModel.valid()
        }

    }

    companion object {
        const val MIN_PASSWORD_CHAR_COUNT = 6
    }
}