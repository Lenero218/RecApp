package com.example.recapp.Network

import com.example.recapp.Network.Response.RecipeSearchResponse
import com.example.recapp.Network.model.RecipeDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeService {
    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ):RecipeSearchResponse //Will return RecipeSearchResponse, this will return all the available recipes

    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id:Int
    ):RecipeDTO//Will return a particular recipe
}