package com.example.recapp.repository

import com.example.recapp.Network.RecipeService
import com.example.recapp.Network.model.RecipeDTO
import com.example.recapp.Network.model.RecipeDtoMapper
import com.example.recapp.domain.model.Recipe
import com.example.recapp.domain.util.DomainMapper

class RecipeRepositoryImplementation(private val recipeService:RecipeService,
private val mapper:RecipeDtoMapper):RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
       val result=recipeService.search(token, page, query).recipes //This will return the list of recipe dtos
        return mapper.toDomainList(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
return mapper.mapToDomainModel(recipeService.get(token, id))
    }
}