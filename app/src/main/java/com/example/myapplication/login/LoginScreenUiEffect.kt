package com.example.myapplication.login

import androidx.annotation.StringRes

sealed class LoginScreenUiEffect {
    data class ShowValidationError(@StringRes val errorId: Int) : LoginScreenUiEffect()
    object NavigateToOtherScreen : LoginScreenUiEffect()
}
