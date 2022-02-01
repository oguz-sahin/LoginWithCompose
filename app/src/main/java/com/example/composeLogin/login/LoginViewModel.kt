package com.example.composeLogin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeLogin.login.LoginScreenUiEffect.NavigateToOtherScreen
import com.example.composeLogin.login.LoginScreenUiEffect.ShowValidationError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {


    private var _loginUiState = MutableStateFlow(LoginUiState.initial())
    val loginUiState get() = _loginUiState.asStateFlow()

    private var _loginScreenUiEffect = Channel<LoginScreenUiEffect>(Channel.BUFFERED)
    val loginScreenUiEffect get() = _loginScreenUiEffect.receiveAsFlow()

    fun login() {
        if (checkValidation().not()) return

        // user login successful

        sendUiEffect(NavigateToOtherScreen)
    }


    fun onEmailChange(value: String) {
        if (value.length < EMAIL_MAX_CHARACTER_COUNT) {
            _loginUiState.update { currentUiState ->
                currentUiState.copy(email = Email(value.trim()))
            }
        }
    }

    fun onPasswordChange(value: String) {
        if (value.length < PASSWORD_MAX_CHARACTER_COUNT) {
            _loginUiState.update { currentUiState ->
                currentUiState.copy(password = Password(value.trim()))
            }
        }
    }


    private fun checkValidation(): Boolean = isEmailValid() && isPasswordValid()

    private fun isEmailValid(): Boolean {
        val emailValidation = _loginUiState.value.email.checkValidation()
        if (emailValidation.isValid.not()) {
            sendUiEffect(uiEffect = ShowValidationError(emailValidation.errorMessageId!!))
        }
        return emailValidation.isValid
    }

    private fun isPasswordValid(): Boolean {
        val passwordValidation = _loginUiState.value.password.checkValidation()
        if (passwordValidation.isValid.not()) {
            sendUiEffect(uiEffect = ShowValidationError(passwordValidation.errorMessageId!!))
        }
        return passwordValidation.isValid
    }

    private fun sendUiEffect(uiEffect: LoginScreenUiEffect) {
        viewModelScope.launch {
            _loginScreenUiEffect.send(uiEffect)
        }
    }

    companion object {
        const val EMAIL_MAX_CHARACTER_COUNT = 40
        const val PASSWORD_MAX_CHARACTER_COUNT = 12
    }

}

