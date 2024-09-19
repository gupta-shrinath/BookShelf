package com.droid.bookshelf.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droid.bookshelf.ui.theme.BookShelfTheme
import com.droid.bookshelf.ui.viewmodels.Async
import com.droid.bookshelf.utils.isValidEmail
import com.droid.bookshelf.utils.isValidPassword
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach

@Composable
fun SignUpScreen(countriesFlow: Flow<Async<List<String>>>, goToNextScreen: () -> Unit) {

    val countriesListState by countriesFlow.collectAsState(initial = Async.Loading)

    when (countriesListState) {
        is Async.Error -> {
            Text(text = "Error fetching countries")
        }

        is Async.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Loading countries")
                }
            }
        }

        is Async.Success -> {
            val countries = (countriesListState as Async.Success).data
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var selectedCountry by remember { mutableStateOf(countries.firstOrNull() ?: "") }
            var passwordVisible by remember { mutableStateOf(false) }
            var expanded by remember { mutableStateOf(false) }
            val interactionSource = remember {
                object : MutableInteractionSource {
                    override val interactions = MutableSharedFlow<Interaction>(
                        extraBufferCapacity = 16,
                        onBufferOverflow = BufferOverflow.DROP_OLDEST,
                    )

                    override suspend fun emit(interaction: Interaction) {
                        if (interaction is PressInteraction.Release) {
                            expanded = true
                        }

                        interactions.emit(interaction)
                    }

                    override fun tryEmit(interaction: Interaction): Boolean {
                        return interactions.tryEmit(interaction)
                    }
                }
            }
            var isSignupClicked by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Email TextField
                OutlinedTextField(
                    value = email,
                    isError = isSignupClicked && email.isValidEmail().not(),
                    supportingText = {
                        if (isSignupClicked && email.isValidEmail().not()) {
                            Text(text = "Invalid Email")
                        }
                    },
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password TextField
                OutlinedTextField(
                    value = password,
                    isError = isSignupClicked && password.isValidPassword().not(),
                    supportingText = {
                        if (isSignupClicked && password.isValidPassword().not()) {
                            Text(
                                text = """
                                Password requirements: Minimum of 8 characters, including at least one number, one
                                special character -> !@#${'$'}%^&*(), one lowercase letter, and one uppercase letter.
                            """.trimIndent()
                            )
                        }
                    },
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        //  val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            I//con(imageVector = icon, contentDescription = null)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCountry,
                        onValueChange = { selectedCountry = it },
                        interactionSource = interactionSource,
                        label = { Text("Select Country") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expanded = true
                            },
                        readOnly = true,
                        trailingIcon = {
                            IconButton({}) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = { Text(text = country) },
                                onClick = {
                                    selectedCountry = country
                                    expanded = false
                                })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sign Up Button
                Button(
                    onClick = {
                        isSignupClicked = true
                        if (email.isValidEmail().not()) {
                            return@Button
                        } else if (password.isValidPassword().not()) {
                            return@Button
                        }
                        goToNextScreen()
                        // Validate
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    BookShelfTheme {
        SignUpScreen(flow {
            emit(Async.Success(listOf("India", "US")))
        })
        {}
    }
}