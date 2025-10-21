package com.example.loginfirebase

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface SWAPI {
    @Headers("Accept: application/json")
    // Metodo para obtener todos los pokemon

    @GET("people")
    fun getJedais(): Call<StarWarsResponse>

    // Metodo para obtener una pokemon por su ID
    @GET("people/{id}")
    fun getJedai(@Path("id") id: Int): Call<Jedai>

}