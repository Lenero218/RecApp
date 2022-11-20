package com.example.recapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold

import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.recapp.presentation.Components.CircularIndeterminateProgressBar
import com.example.recapp.presentation.Components.RecipeView
import com.example.recapp.presentation.MainApplication
import com.example.recapp.presentation.Theme.AppTheme
import com.example.recapp.presentation.ui.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReceipeFragment: Fragment() {

    @Inject
    lateinit var application:MainApplication





    private val viewModel:recipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let{
            recipeId->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading=viewModel.loading.value
                val recipe=viewModel.recipe.value



                AppTheme(darkTheme = application.isDark.value) {
                    Scaffold(scaffoldState = rememberScaffoldState(),
                    ) {
                        Box(modifier=Modifier.fillMaxWidth()){
                          if (loading && recipe==null){
                              Text(text = "Loading...")
                          }else{
                              recipe?.let{
                                  RecipeView(recipe = it)
                              }
                          }
                        }
                        CircularIndeterminateProgressBar(isDisplayed = loading)
                    }
                    
                }

                }
                

            }
        }
    }

