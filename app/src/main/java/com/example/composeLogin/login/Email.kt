package com.example.composeLogin.login

import android.util.Patterns.EMAIL_ADDRESS
import com.example.composeLogin.Validation

@JvmInline
value class Email(val value: String) : Validation {

    override fun checkValidation(): ValidationModel {

        return when {
            value.isEmpty() -> ValidationModel(
                isValid = false,
                errorMessageId = StringRes.email_empty_error
            )
            EMAIL_ADDRESS.matcher(value).matches().not() -> ValidationModel(
                isValid = false,
                errorMessageId = StringRes.wrong_email_format_error
            )
            else -> ValidationModel.valid()
        }
    }

}