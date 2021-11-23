package com.runningapp.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    val focusRequester = remember {
        FocusRequester()
    }
    val usernameSate = remember { mutableStateOf("") }
    val passwordSate = remember { mutableStateOf("") }
    val isVisibility = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)).fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Hello, create a new account!",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Icon(
                    Icons.Filled.DirectionsRun,
                    contentDescription = "Runner",
                    modifier = Modifier.size(48.dp)
                )
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(),
            value = usernameSate.value,
            onValueChange = {
                usernameSate.value = it
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            label = {
                Text(
                    "Name",
                    //style = androidx.compose.material3.MaterialTheme.typography.labelLarge
                ) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),

            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            )
        )

        OutlinedTextField(
            modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(),
            value = usernameSate.value,
            onValueChange = {
                usernameSate.value = it
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            label = {
                Text(
                    "Email",
                    //style = androidx.compose.material3.MaterialTheme.typography.labelLarge
                ) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),

            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            )
        )

        OutlinedTextField(
            modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth().focusRequester(focusRequester),
            value = passwordSate.value,
            onValueChange = { passwordSate.value = it },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            label = {
                Text(
                    "Password",
                    // style = androidx.compose.material3.MaterialTheme.typography.labelLarge
                ) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isVisibility.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                androidx.compose.material3.IconButton(onClick = { isVisibility.value = !isVisibility.value }) {
                    androidx.compose.material3.Icon(
                        imageVector = if (isVisibility.value) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            },

            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        androidx.compose.material3.Button(
            modifier = Modifier.padding(top = 16.dp),
            enabled = usernameSate.value.isNotEmpty() && passwordSate.value.isNotEmpty(),
            onClick = { /*TODO*/ }) {
            androidx.compose.material3.Text("Register")
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Already have an account? Sign in!",
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}