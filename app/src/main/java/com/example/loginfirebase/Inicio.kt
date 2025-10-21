package com.example.loginfirebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginfirebase.ui.theme.LoginFirebaseTheme
import kotlin.jvm.java


class Inicio : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginFirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    var contexto1 = LocalContext.current

    val imagen1 = R.drawable.logoluisvives


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





        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                contexto1.startActivity(Intent(contexto1, Login::class.java))
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

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    LoginFirebaseTheme {
        Greeting2("Android")
    }
}