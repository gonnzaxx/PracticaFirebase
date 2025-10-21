package com.example.loginfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth

class Login : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginFirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    fun login(email: String, password: String) {
        //verificar que no están vacías
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //le envío a mostrar
                        val intent = Intent(this, Datos::class.java)
                        startActivity(intent)
                    } else {
                        //toast de error
                        Toast.makeText(this, "Email o contraseña inválidos", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else {
            Toast.makeText(this, "Inserta un email y una contraseña", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var contexto1 = LocalContext.current //damos contexto

    val imagen1 = R.drawable.logoluisvives

    var campoEmail by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var campoContrasena by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var mensajeError by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {


        Text(
            text = "RESERVIVES",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(red = 181, green = 36, blue = 106)
        )
        Text(
            text = "Gestiona. Reserva. Disfruta.",
            fontSize = 14.sp,
            color = Color(red = 181, green = 36, blue = 106),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = imagen1),
            contentDescription = "logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))


        OutlinedTextField(
            value = campoEmail,
            onValueChange = { x ->
                campoEmail = x
            },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = campoContrasena,
            onValueChange = { x ->
                campoContrasena = x
            },
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation('*')
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val activity = contexto1 as Login  // convertimos el contexto (es un tipo generico) a la Activity Login ya que el
                contexto1.login(                    //metodo login no existe en la clase Context sino en la de Login.kt
                    campoEmail.text.trim(),
                    campoContrasena.text.trim()
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 181, green = 36, blue = 106),
                contentColor = Color.White
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(55.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 10.dp
            )
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "¿No tienes cuenta?"
        )
        Text(
            text = "Registrarse",
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable() {
                val intent1 = Intent(contexto1, Registro::class.java)
                contexto1.startActivity(intent1)
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginFirebaseTheme {
        Greeting("Android")
    }
}