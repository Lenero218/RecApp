package com.example.recapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recapp.domain.model.Recipe
import com.example.recapp.repository.RecipeRepository
import com.example.recapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE=30

const val STATE_KEY_PAGE="recipe.page.state.key"
const val STATE_KEY_QUERY="recipe.state.query.key"
const val STATE_KEY_LIST_POSITION="recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY="recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel
@Inject //This is used to import a Hilt inside a ViewModel
constructor(
    private val repository:RecipeRepository,
    private @Named("auth_token") val token:String,
    private val savedStateHandle:SavedStateHandle,//Because of ViewModelInject we are able to use this and there is no need to
//make any type of factory to make use of this

    ): ViewModel() {
         val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
         val query= mutableStateOf("")
    val selectedCategory:MutableState<FoodCategory?> = mutableStateOf(null)
     var categoryScrollPosition:Float=0f
    val page= mutableStateOf(1)
    private var recipeListScrollPosition=0

    val loading= mutableStateOf(false)

       init {
           savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let{
               p->
               setPage(p)
           }
           savedStateHandle.get<String>(STATE_KEY_QUERY)?.let{
               q->
               setQuery(q)
           }
           savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let{
               SC->
               setSelectedCategory(SC)
           }
           savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let{
              LP->
               setListScrollPosition(LP)
           }


           if(recipeListScrollPosition!=0){
           onTriggerEvent(RecipeListEvent.RestoreStateEvent)
           }else{
               onTriggerEvent(RecipeListEvent.NewSearchEvent)
           }


       onTriggerEvent(RecipeListEvent.NewSearchEvent)
       }

    fun onTriggerEvent(event:RecipeListEvent){
        viewModelScope.launch {
            try{
                when(event){
                    is RecipeListEvent.NewSearchEvent->{
newSearch()
                    }
                    is RecipeListEvent.NextPageEvent->{
nextPage()
                    }
                    is RecipeListEvent.RestoreStateEvent->{
                        restoreState()
                    }
                }


            }catch (e:Exception){
                Log.e(TAG,"onTriggerEvent:Exception: ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun restoreState(){
        loading.value=true
        val results:MutableList<Recipe> = mutableListOf()
    for(p in 1..page.value){
        val result=repository.search(
            token=token,
                page=p,
            query = query.value
        )
        results.addAll(result)
        if(p==page.value){
recipes.value=results
        loading.value=false
        }
    }


    }


    //use case #1
  private suspend  fun newSearch(){


            loading.value=true
            resetSearchState()

            val result=repository.search(
                token = token,
                page=1,
                query = query.value,
            )
            recipes.value=result
            loading.value=false

    }
  //use case #2
  private suspend  fun nextPage(){

            // prevent duplicate event due to recompose happening to quickly
            if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE) ){
                loading.value = true
                incrementPageNumber()
                Log.d(TAG, "nextPage: triggered: ${page.value}")

                // just to show pagination, api is fast
                delay(1000)

                if(page.value > 1){
                    val result = repository.search(token = token, page = page.value, query = query.value )
                    Log.d(TAG, "search: appending")
                    appendRecipes(result)
                }
                loading.value = false
            }

    }



    //For appending new recipes to the current list of recipes
    private fun appendRecipes(recipes:List<Recipe>){
        val current=ArrayList(this.recipes.value) //For getting the current list
        current.addAll(recipes)
        this.recipes.value=current //This will set the current recipes to the next page recipes
    }


    private fun incrementPageNumber(){
       setPage(page.value+1)
    }

    fun onChangeRecipeScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }


    private fun resetSearchState(){   //Clearing the state when there is another category is in search
        recipes.value= listOf()
        page.value=1
        onChangeRecipeScrollPosition(0)

        if (selectedCategory.value?.value!=query.value){
            clearSelectedCategory()
        }
    }

    private fun  clearSelectedCategory(){
        setSelectedCategory(null)
    }



    fun onQueryChange(query:String){
        setQuery(query)
    }

    fun onSelectedCategoryChange(category:String){
        val newCategory= getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChange(category)
    }

    fun onChangeCategoryScrollPosition(position:Float){
        categoryScrollPosition=position
    }

    private fun setListScrollPosition(position:Int){
        recipeListScrollPosition=position
        savedStateHandle.set(STATE_KEY_LIST_POSITION,position)
    }

    private fun setPage(page:Int){
        this.page.value=page
        savedStateHandle.set(STATE_KEY_PAGE,page)
    }

    private fun setSelectedCategory(category:FoodCategory?){
selectedCategory.value=category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY,category)
    }

    private fun setQuery(query:String){
        this.query.value=query
        savedStateHandle.set(STATE_KEY_QUERY,query)//Passing the values to the state key query in saved state handle
    }





    }