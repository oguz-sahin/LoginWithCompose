package com.example.myapplication

import com.example.myapplication.login.ValidationModel

interface Validation {
    fun checkValidation(): ValidationModel
}