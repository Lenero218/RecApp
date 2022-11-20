package com.example.recapp.presentation.ui.recipe

sealed   class RecipeEvent {
data class GetRecipeEvent(val id:Int):RecipeEvent()//To pass arguments we defined it as data class


}