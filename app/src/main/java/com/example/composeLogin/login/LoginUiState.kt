package com.example.composeLogin.login


data class LoginUiState(
    val email: Email,
    val password: Password
) {
    companion object {
        fun initial() = LoginUiState(
            email = Email(""),
            password = Password("")
        )
    }
}
