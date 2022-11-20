package com.example.recapp.presentation.ui.recipe_list

enum class FoodCategory(val value:String) {
CHICKEN("Chicken"),
    BEEF("Beef"),
SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun getAllFoodCatogories():List<FoodCategory>{
    return listOf(FoodCategory.CHICKEN,FoodCategory.BEEF,FoodCategory.DESSERT,FoodCategory.DONUT,FoodCategory.MILK,FoodCategory.PIZZA,FoodCategory.SOUP,
    FoodCategory.VEGETARIAN,FoodCategory.VEGAN)
}

fun getFoodCategory(value: String):FoodCategory?{
    val map=FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}
