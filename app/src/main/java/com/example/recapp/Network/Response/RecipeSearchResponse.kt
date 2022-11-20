package com.example.recapp.Network.Response

import com.example.recapp.Network.model.RecipeDTO
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    var count:Int,
    @SerializedName("results")
    var recipes :List<RecipeDTO>,

    ) {
}