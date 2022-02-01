package com.example.composeLogin.login


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeLogin.R
import com.example.composeLogin.login.LoginScreenUiEffect.ShowValidationError
import kotlinx.coroutines.flow.collectLatest


typealias StringRes = R.string

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val loginUiState by loginViewModel.loginUiState.collectAsState()
    var passwordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current



    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState
    ) {

        LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
            loginViewModel.loginScreenUiEffect.collectLatest { loginScreenUiEffect ->
                when (loginScreenUiEffect) {
                    is ShowValidationError -> {
                        val errorMessage = context.resources.getString(loginScreenUiEffect.errorId)
                        scaffoldState.snackbarHostState.showSnackbar(errorMessage)
                    }
                    LoginScreenUiEffect.NavigateToOtherScreen -> {
                        Toast.makeText(context, "NavigatingPage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.padding(top = 48.dp))

            Text(
                text = "Login",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                value = loginUiState.email.value,
                onValueChange = { email ->
                    loginViewModel.onEmailChange(email)
                },
                label = { Text("Email") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue
                ),
                maxLines = 2,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                singleLine = true,
                placeholder = { Text(text = stringResource(id = StringRes.email_placeholder)) }
            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = loginUiState.password.value,
                label = { Text("Password") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue
                ),
                onValueChange = { password ->
                    loginViewModel.onPasswordChange(password)
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon =
                        if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        passwordVisibility = passwordVisibility.not()
                    }) {
                        Icon(icon, null)
                    }
                },
                maxLines = 2,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    loginViewModel.login()
                }),
                placeholder = { Text(text = stringResource(id = StringRes.password_placeholder)) }
            )


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .clip(RoundedCornerShape(40.dp)),
                onClick = {
                    focusManager.clearFocus()
                    loginViewModel.login()
                },
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }

}

