package com.example.composeLogin

import com.example.composeLogin.login.ValidationModel

interface Validation {
    fun checkValidation(): ValidationModel
}