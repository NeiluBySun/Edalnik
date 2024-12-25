package com.edalnik.edalnik.view.screens

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edalnik.edalnik.model.FoodItem
import com.edalnik.edalnik.viewmodel.FoodViewModel
import com.edalnik.edalnik.R
import kotlinx.coroutines.launch


@Composable
fun FoodAppendingScreen(viewModel: FoodViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center

    ) {
        ExpandableFoodList(
            viewModel
        )
    }
}


@Composable
fun ExpandableFoodList(
    viewModel: FoodViewModel
) {
    var isAddExpanded by remember { mutableStateOf(false) }
    var isCartExpanded by remember { mutableStateOf(true) }
    val chosenFoodItems by viewModel.chosenFood.collectAsState()

    val foodItems = viewModel.getFoodList()

    var searchQuery by remember { mutableStateOf("") }
    val filteredFoodItems = remember(searchQuery, foodItems) {
        if (searchQuery.isEmpty()) {
            foodItems
        } else {
            foodItems.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = if (isCartExpanded) "Добавленные продукты" else "Добавьте продукты",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 27.sp,
            fontFamily = FontFamily.SansSerif
        )
        AnimatedVisibility(
            visible = isCartExpanded,
            enter = expandVertically(),
            exit = shrinkVertically(),

        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.90F)
                    .height(400.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF393737))
                    .border(
                        BorderStroke(2.dp, Color(0xFF7E7E7E)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 10.dp)

            ) {
                items(chosenFoodItems) { foodItem ->
                    FoodItemRow(
                        foodItem = foodItem,
                        onFoodRowClick = {
                                reducedFoodItem -> viewModel.reduceChosenAmount(reducedFoodItem)
                        },
                        isChosen = foodItem.isChosen,
                        onDeleteAllAmount = {
                            deletedFoodItem -> viewModel.deleteChosenRow(deletedFoodItem)
                        }
                    )
                }
            }
        }
        Button(
            onClick = {
                isAddExpanded = !isAddExpanded
                isCartExpanded = !isCartExpanded
            },
            modifier = Modifier
                .width(190.dp)
                .height(50.dp)
                .padding(1.dp)

            ,
            border = BorderStroke(2.dp, Color(0xFF7E7E7E)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF393737),
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),

        ) {
            Text(
                text = if (isCartExpanded) "добавить продукты" else "закрыть список",
                modifier = Modifier.padding(0.dp),
                color = Color.White
            )
        }

        AnimatedVisibility(
            visible = isAddExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text("Поиск продуктов") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                            }
                        }
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    items(filteredFoodItems) { foodItem ->
                        FoodItemRow(
                            foodItem = foodItem,
                            onFoodRowClick = {
                                appendingFoodItem -> viewModel.addFood(appendingFoodItem)
                            }
                        )
                    }
                }
                if (filteredFoodItems.isEmpty()) {
                    Text(
                        text = "Такой еды в нашем списке нет",
                        modifier = Modifier
                            .padding(0.dp)
                            .alpha(0.5F),
                        color = Color.White

                    )
                }
            }
        }
    }
}

@Composable
fun FoodItemRow(
    foodItem: FoodItem,
    onFoodRowClick: (FoodItem) -> Unit,
    isChosen: Boolean = false,
    onDeleteAllAmount: (FoodItem) -> Unit = {_ ->}
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${foodItem.name} ",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = foodItem.calories,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            if(isChosen) {
                val amount by foodItem.amount.collectAsState()
                Text(
                    text = "x${amount}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        IconButton(
            onClick = {
                coroutineScope.launch {
                    onFoodRowClick(foodItem)
                }},
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(8.dp),

            ) {
            Icon(
                painter = painterResource(
                    if (isChosen) R.drawable.minus else R.drawable.plus
                ),
                contentDescription = "Delete all chosen",
                tint = Color(0xFF7E7E7E)
            )
        }
        if(isChosen) {
            IconButton(
                onClick = {onDeleteAllAmount(foodItem)},
                modifier = Modifier
                    .width(45.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(16.dp)),

                ) {
                Icon(
                    painter = painterResource(R.drawable.trashcan),
                    contentDescription = "Delete all chosen",
                    tint = Color(0xFF7E7E7E)
                )
            }
        }
    }
}


