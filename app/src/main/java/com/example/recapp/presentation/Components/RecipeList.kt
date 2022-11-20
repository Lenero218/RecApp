package com.example.recapp.presentation.Components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.recapp.R
import com.example.recapp.domain.model.Recipe
import com.example.recapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.recapp.presentation.ui.recipe_list.RecipeListEvent

@Composable
fun RecipeList(
    loading:Boolean,
    recipes:List<Recipe>,
    onChangeRecipeScrollPosition: (Int)->Unit,
    page:Int,
    onNextPage:(RecipeListEvent)->Unit,
    navController:NavController



){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ){
        LazyColumn {
            itemsIndexed(
                items = recipes
            ){index, recipe ->
                onChangeRecipeScrollPosition(index)
                if((index+1)>=(page* PAGE_SIZE)&& !loading){
                  onNextPage(RecipeListEvent.NextPageEvent)
                }

                RecipeCard(recipe = recipe, onClick = {
                    if(recipe.id!=null){
                        val bundle=Bundle()
                        bundle.putInt("recipeId",recipe.id)
                        navController.navigate(R.id.viewRecipe,bundle)
                    }

                })
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading) //This shuld be at the bottom so that it comes on the top of Lazy Column
    }
}