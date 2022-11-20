package com.example.recapp

import android.media.MediaRouter2
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.recapp.presentation.Components.*
import com.example.recapp.presentation.MainApplication
import com.example.recapp.presentation.Theme.AppTheme
import com.example.recapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.recapp.presentation.ui.recipe_list.RecipeListEvent


import com.example.recapp.presentation.ui.recipe_list.RecipeListViewModel
import com.example.recapp.presentation.ui.recipe_list.getAllFoodCatogories
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var application: MainApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {


                





                AppTheme(darkTheme = application.isDark.value,
                ){
                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    val selectedCategory =viewModel.selectedCategory.value

                    val loading=viewModel.loading.value

                    val page=viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChange = viewModel::onQueryChange,
                                onExecuteSearch = {viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)},
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChange,
                                onToggleTheme={
                                    application.toggleLightTheme()
                                }
                            )
                        },
                        bottomBar = {
//MyBottomBar()
                        },
                        drawerContent = {
                       //     MyDrawer()
                        }

                    ) {
RecipeList(
    loading = loading,
    recipes = recipes,
    onChangeRecipeScrollPosition = { viewModel::onChangeRecipeScrollPosition },
    page = page,
    onNextPage = {
                     viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
    },
    navController = findNavController()
)
                    }


                    Column {

//                        ShimmerRecipeCardItem(colors = listOf(Color.LightGray.copy(alpha=0.9f),
//                            Color.LightGray.copy(alpha=0.2f),
//                            Color.LightGray.copy(alpha=0.9f)) ,
//                            cardHeight = 250.dp)



                    }
                }
            }
        }
    }

}

//@Composable
//
//fun GradientDemo(){
//    val colors= listOf(
//Color.Blue,
//        Color.Red,
//        Color.Blue
//    )
//    val brush=linearGradient(
//        colors,
//        start= Offset(200f,200f),
//        end= Offset(400f,400f)
//    )
//    Surface(shape=MaterialTheme.shapes.small) {
//        Spacer( modifier= Modifier
//            .fillMaxSize()
//            .background(brush = brush))
//
//    }
//}


//@Composable
//fun MyBottomBar(){
//    BottomNavigation(
//        elevation=12.dp
//    ) {
//        BottomNavigationItem(selected =false , onClick = {  },icon = {
//            Image(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription =null )
//        })
//
//        BottomNavigationItem(selected =true , onClick = {  },icon = {
//            Image(painter = painterResource(id = R.drawable.ic_baseline_message_24), contentDescription =null )
//        })
//
//        BottomNavigationItem(selected =false , onClick = {  },icon = {
//            Image(painter = painterResource(id = R.drawable.ic_baseline_mic_none_24), contentDescription =null )
//        })
//    }
//}
//
//@Composable
//fun MyDrawer(controller:NavController){
//    Column {
//         Text(text = "Settings",style= TextStyle(Color.Red),fontFamily = FontFamily.Cursive)
//        Spacer(modifier = Modifier.padding(10.dp))
//
//        Text(text = "Account Privacy",style= TextStyle(Color.Red),fontFamily = FontFamily.Cursive)
//        Spacer(modifier = Modifier.padding(10.dp))
//
//        Text(text = "Notifications",style= TextStyle(Color.Red),fontFamily = FontFamily.Cursive)
//        Spacer(modifier = Modifier.padding(10.dp))
//
//        Text(text = "More Food",style= TextStyle(Color.Red),fontFamily = FontFamily.Cursive)
//        Spacer(modifier = Modifier.padding(10.dp))
//
//        Text(text = "Order Now",style= TextStyle(Color.Red),fontFamily = FontFamily.Cursive)
//        Spacer(modifier = Modifier.padding(10.dp))
//    }
//}

@Composable
fun DecopledSnackBarDemo(
    snackbarHostState: SnackbarHostState
){
    ConstraintLayout(modifier=Modifier.fillMaxSize()) {
        val snackbar=createRef()
        SnackbarHost(
            modifier=Modifier.constrainAs(snackbar){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action =
                    {
                        TextButton(
onClick = {
    snackbarHostState.currentSnackbarData?.dismiss()
}
                                ){
                            Text(text = snackbarHostState.currentSnackbarData?.actionLabel?:"",
                            style= TextStyle(color=Color.White)
                            )
                        }


                    }
                ) {
                    Text(text =  snackbarHostState.currentSnackbarData?.message?:" ")
                }
            }
        )
    }
}







@Composable
fun SnackBarDemo(
    isShowing:Boolean,
    onHideSnackBar:()->Unit
){
    if (isShowing){
        ConstraintLayout(modifier=Modifier.fillMaxSize()) {
            val snackbar=createRef()
            Snackbar(
                modifier=Modifier.constrainAs(snackbar){
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action =
                {
                    Text(text="Hide",
                        modifier=Modifier.clickable(
                            onClick = onHideSnackBar,

                            ),
                        style= MaterialTheme.typography.h5
                    )
                }
            ) {
                Text(text = "Hey Look a snack Bar")
            }
        }
    }



}
