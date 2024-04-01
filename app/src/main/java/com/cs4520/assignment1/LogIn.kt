package com.cs4520.assignment1

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cs4520.assignment1.model.Credentials

@Composable
fun LogInScreen (
    navHostController: NavHostController,
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            val context = LocalContext.current
            var credentials by remember { mutableStateOf(Credentials()) }
            LoginField(
                value = credentials.login,
                onChange = { credentials = credentials.copy(login = it) },
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(
                value = credentials.pwd,
                onChange = { credentials = credentials.copy(pwd = it) },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (checkCredentials(credentials, context)) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(Screen.PRODUCTLIST.name)
                        // move to product list fragment
                    }
                },
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    label: String = "Login",
) {
    TextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    label: String = "Password",
) {
    TextField(
        value = value,
        onValueChange = onChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        label = { Text(label) }
    )
}

fun checkCredentials(creds: Credentials, context: Context): Boolean {
    if (creds.isNotEmpty() && creds.login == "admin" && creds.pwd == "admin") {
        return true
    } else {
        Toast.makeText(context, "Invalid username", Toast.LENGTH_SHORT).show()
        return false
    }
}