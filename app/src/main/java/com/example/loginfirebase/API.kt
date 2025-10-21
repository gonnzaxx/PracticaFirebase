package com.example.loginfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginfirebase.ui.theme.LoginFirebaseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API : ComponentActivity() {

    private lateinit var retrofit: Retrofit
    private var texto : String = "Star Wars API"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/") //url de la API de star wars
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        texto = obtenerDatos(retrofit)

        enableEdgeToEdge()
        setContent {
            LoginFirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting4(
                        name = texto,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    private fun obtenerDatos(retrofit : Retrofit) : String{
        var texto = ""

        CoroutineScope(Dispatchers.IO).launch {
            val call =
                retrofit.create(SWAPI::class.java).getJedais().execute()
            val jedais = call.body()
            if(call.isSuccessful){
                texto = jedais?.results?.get(1)?.getNombre().toString()
            }else{
                texto = "Ha habido un error"
            }
        }
        Thread.sleep(1000)
        return texto;
    }
}

@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "API",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(red = 181, green = 36, blue = 106),
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)

        )

        Text(
            text = "Hola $name!",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    LoginFirebaseTheme {
        Greeting4("Android")
    }
}