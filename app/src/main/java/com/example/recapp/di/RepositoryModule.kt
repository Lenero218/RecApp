package com.example.recapp.di

import androidx.transition.Visibility
import com.example.recapp.Network.RecipeService
import com.example.recapp.Network.model.RecipeDtoMapper
import com.example.recapp.repository.RecipeRepository
import com.example.recapp.repository.RecipeRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
@Singleton
@Provides

fun providesRecipeRepository(
    recipeService: RecipeService,
    recipeDtoMapper:RecipeDtoMapper
):RecipeRepository{
    return RecipeRepositoryImplementation(
recipeService,recipeDtoMapper
    )
}

}