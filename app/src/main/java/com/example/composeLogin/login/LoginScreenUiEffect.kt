package com.example.composeLogin.login

import androidx.annotation.StringRes

sealed class LoginScreenUiEffect {
    data class ShowValidationError(@StringRes val errorId: Int) : LoginScreenUiEffect()
    object NavigateToOtherScreen : LoginScreenUiEffect()
}
