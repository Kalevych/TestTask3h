package com.afkoders.testtask18feb.data.network

import com.afkoders.testtask18feb.data.network.models.ButtonActionsResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */


interface ButtonActionService{
    @GET("androidexam/butto_to_action_config.json")
    suspend fun getButtonActions(): ButtonActionsResponse
}

/**
 * Main entry point for network access. Call like `ButtonActionServiceImpl.buttonActions.getActions() or smth`
 */
object ButtonActionServiceImpl {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://s3-us-west-2.amazonaws.com//")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val buttonActionsServiceImpl = retrofit.create(ButtonActionService::class.java)

    // should be handled by dagger
}