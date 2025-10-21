package com.example.loginfirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth

class Datos : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginFirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting5(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {

    var contexto1 = LocalContext.current


    var campoEmail by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var campoContrasena by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var mensajeError by remember {
        mutableStateOf("")
    }

    // Acceso a SharedPreferences
    val prefs = remember {
        contexto1.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    }

    // 3) Cargar valores guardados una sola vez
    LaunchedEffect(Unit) {
        campoEmail = TextFieldValue(prefs.getString("email", "") ?: "")
        campoContrasena = TextFieldValue(prefs.getString("contrasena", "") ?: "")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Datos personales",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(red = 181, green = 36, blue = 106),
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)

        )


        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(
            value = campoEmail,
            onValueChange = { x ->
                campoEmail = x
            },
            label = { Text(text = "E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = campoContrasena,
            onValueChange = { x ->
                campoContrasena = x
            },
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation('*')
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                //añadimos los campos del SharedPrefs a los campos del formulario
                prefs.edit().apply {
                    putString("email", campoEmail.text.trim())
                    putString("contrasena", campoContrasena.text)
                    apply()
                }
                mensajeError = "Cambios guardados correctamente"

                val intent1 = Intent(contexto1, API::class.java) //pasamos a la activity API
                contexto1.startActivity(intent1)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 181, green = 36, blue = 106),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(55.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 10.dp
            )
        ) {
            Text("Guardar cambios")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    LoginFirebaseTheme {
        Greeting5("Android")
    }
}