package com.edalnik.edalnik.view.screens
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edalnik.edalnik.model.FoodItem
import com.edalnik.edalnik.viewmodel.FoodViewModel


@Composable
fun FoodAppendingScreen(viewModel: FoodViewModel) {
    val selectedItems = remember { mutableStateListOf<FoodItem>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center

    ) {
        ExpandableFoodList(
            viewModel.getFoodList()
        )
    }
}


@Composable
fun ExpandableFoodList(
    foodItems: List<FoodItem>,
) {
    var isAddExpanded by remember { mutableStateOf(false) }
    var isCartExpanded by remember { mutableStateOf(true) }

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
            text = "Добавленные продукты",
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
            ) {}
        }
        Button(
            onClick = {
                isAddExpanded = !isAddExpanded
                isCartExpanded = !isCartExpanded
            },
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(8.dp),

        ) {
            Text(text = if (isAddExpanded) "Свернуть список" else "Выбрать продукты")
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
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FoodItemRow(
    foodItem: FoodItem,
) {
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
                text = foodItem.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = foodItem.calories,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        Button(
            onClick = {

            },
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(8.dp),

            ) {}

    }
}


