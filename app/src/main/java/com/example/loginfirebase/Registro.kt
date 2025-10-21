package com.example.loginfirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginfirebase.Inicio
import com.example.loginfirebase.Login
import com.example.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val PREFS = "sharedPrefs"
private const val CLAVE_EMAIL = "email"
private const val CLAVE_CONTRASENA = "contrasena"

class Registro : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)

        var nombre = intent.getStringExtra("nombre")

        if (nombre == null){
            nombre = "No hay nombre"
        }

        enableEdgeToEdge()
        setContent {
            LoginFirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting3(
                        name = nombre,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    fun registro(email: String, pass: String) {
        if(email.isNotEmpty() && pass.isNotEmpty() ){
            //damos de alta email y contraseña
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this){
                //obtener el id de usuario, sabemos que no es nulo ya que lo acabamos de crear
                val user: FirebaseUser = firebaseAuth.currentUser!!
                verifyEmail(user)
                //tdo ha salido bien, por lo tanto vamos a mostrar
                startActivity(Intent(this, Login::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Inserta los datos bien", Toast.LENGTH_SHORT).show()
        }
    }

    fun verifyEmail(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener(this){
                task->
            if(task.isSuccessful){
                Toast.makeText(this, "Email verificado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Error al verificar email", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {

    var campoEmail by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var campoContrasena by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var mensajeError by remember {
        mutableStateOf("")
    }

    var contexto1 = LocalContext.current


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Registro",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(red = 181, green = 36, blue = 106),
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)

        )

        OutlinedTextField(
            value = campoEmail,
            onValueChange = {
                    x -> campoEmail = x
            },
            label = {Text(text = "E-mail")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = campoContrasena,
            onValueChange = {
                    x -> campoContrasena = x
            },
            label = {Text(text = "Contraseña")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation('*')
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                //creamos variable que guarden lo que se escribe en el formulario
                val email = campoEmail.text.trim()
                val contrasena = campoContrasena.text

                val prefs = contexto1.getSharedPreferences(com.example.loginfirebase.PREFS, Context.MODE_PRIVATE)

                //guardamos los escrito en el formulario en SharedPreferences
                prefs.edit().apply {
                    putString(com.example.loginfirebase.CLAVE_EMAIL, email)
                    putString(com.example.loginfirebase.CLAVE_CONTRASENA, contrasena)
                    apply()
                }
                mensajeError = "Registro correcto"

                val activity = contexto1 as Registro  // convertimos el contexto a la funcion Registro
                activity.registro(      //llamamos a la funcion registro y le pasamos los parametros
                    campoEmail.text.trim(),
                    campoContrasena.text.trim()
                )
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
            Text(text = "Registrarse")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
            )
        }

        Text(
            text = "¿Ya tienes cuenta?"
        )
        Text(
            text = "Iniciar Sesión",
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable() {
                val intent1 = Intent(contexto1, Login::class.java)
                contexto1.startActivity(intent1)
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    LoginFirebaseTheme {
        Greeting3("Android")
    }
}