package com.example.recapp.Network.model

import com.example.recapp.domain.model.Recipe
import com.example.recapp.domain.util.DomainMapper

class RecipeDtoMapper:DomainMapper<RecipeDTO,Recipe> {
    override fun mapToDomainModel(model: RecipeDTO): Recipe {
        return Recipe(
            id=model.pk,
            title=model.title,
            featuredImage = model.featured_image,
            rating = model.rating,
            publisher = model.publisher,
            sourceUrl = model.source_url,
            description = model.description,
            cookingInstructions = model.cooking_instructions,
            ingredients = model.ingredients?: listOf(),
            dateAdded = model.date_added,
            dateUpdated = model.date_updated,

        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDTO {
        return RecipeDTO(
            pk=domainModel.id,
            title=domainModel.title,
            featured_image = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            source_url = domainModel.sourceUrl,
            description = domainModel.description,
            cooking_instructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients,
            date_added = domainModel.dateAdded,
            date_updated = domainModel.dateUpdated,

            )
    }

    fun toDomainList(initial:List<RecipeDTO>):List<Recipe>{
        return initial.map{mapToDomainModel(it)}
    }

    fun fromDomainList(initial: List<Recipe>):List<RecipeDTO>{
        return initial.map {mapFromDomainModel(it)}
    }
}