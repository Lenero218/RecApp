package com.example.recapp.presentation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.recapp.R
import com.example.recapp.presentation.ui.recipe_list.FoodCategory
import com.example.recapp.presentation.ui.recipe_list.getAllFoodCatogories

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query:String,
    onQueryChange:(String)->Unit,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    onExecuteSearch:()->Unit,
    selectedCategory:FoodCategory?,
    onSelectedCategoryChanged:(String)->Unit,
    onToggleTheme:() ->Unit,

){
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color= MaterialTheme.colors.secondaryVariant,

        elevation = 8.dp,
    ){
        Column() {
            Row(modifier = Modifier.fillMaxWidth()){

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp)
                        .background(MaterialTheme.colors.surface),
                    value = query,
                    onValueChange = {
                        onQueryChange(it)
                    },
                    label = {
                        Text(text = "Search",
                        color= MaterialTheme.colors.onSurface)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,


                        ),
                    leadingIcon = {
                        Image(
                            painter = painterResource(
                                id = R.drawable.ic_baseline_search_24
                            ),
                            contentDescription = null,
                        )


                    },

                    keyboardActions = KeyboardActions(onDone =
                    {
                        onExecuteSearch()
                        keyboardController?.hideSoftwareKeyboard()
                    }),


                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    // backgroundColor=MaterialTheme.colors.surface,
                    //Check how to set the background color

                )


              ConstraintLayout(modifier=Modifier.align(Alignment.CenterVertically)){
val menu=createRef()
                  IconButton(onClick = onToggleTheme,
                  modifier=Modifier.constrainAs(menu){
                      end.linkTo(parent.end)
                      top.linkTo(parent.top)
                      bottom.linkTo(parent.bottom)
                  }) {
                        Image(painter = painterResource(id = R.drawable.ic_baseline_more_vert_24), contentDescription =null )
                  }
              }





            }





            val scrollState= rememberScrollState()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 8.dp, bottom = 8.dp),


                ){


                for(category in getAllFoodCatogories()){
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                           onSelectedCategoryChanged(it)
                        },
                        onExecuteSearch = {
                                          onExecuteSearch()
                                          },
                    )
                }
            }
        }
    }

}