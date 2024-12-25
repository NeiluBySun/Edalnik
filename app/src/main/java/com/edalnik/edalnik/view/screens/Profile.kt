package com.edalnik.edalnik.view.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.edalnik.edalnik.model.ActivityLevel
import com.edalnik.edalnik.model.Gender
import com.edalnik.edalnik.viewmodel.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: FoodViewModel) {
    val gender by viewModel.gender.collectAsState()
    val height by viewModel.height.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val age by viewModel.age.collectAsState()
    val activityLevel by viewModel.activityLevel.collectAsState()
    val targetCalories by viewModel.targetCalories.collectAsState()
    val customTargetCalories by viewModel.customTargetCalories.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.setGender(Gender.MALE) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF393737),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color(0xFF7E7E7E))
                ) {
                    Text("Male")
                }
                Button(
                    onClick = { viewModel.setGender(Gender.FEMALE) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF393737),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color(0xFF7E7E7E))

                ) {
                    Text("Female")
                }
            }


            OutlinedTextField(
                value = height.toString(),
                onValueChange = { viewModel.setHeight(it.toIntOrNull() ?: 0) },
                label = { Text("Height (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = weight.toString(),
                onValueChange = { viewModel.setWeight(it.toIntOrNull() ?: 0) },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = age.toString(),
                onValueChange = { viewModel.setAge(it.toIntOrNull() ?: 0) },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )



            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = activityLevel.name,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ActivityLevel.values().forEach { level ->
                        DropdownMenuItem(
                            text = {Text("Уровень нагрузки")},
                            onClick = TODO(),
                            modifier = TODO(),
                            leadingIcon = TODO(),
                            trailingIcon = TODO(),
                            enabled = TODO(),
                            colors = TODO(),
                            contentPadding = TODO()
                        )
                    }
                }
            }

            Text(
                text = "Рекомндуемая норма калорий: ${targetCalories.toInt()}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 30.dp)
            )

//            OutlinedTextField(
//                value = customTargetCalories.toString(),
//                onValueChange = { viewModel.setCustomTargetCalories(it.toFloatOrNull() ?: 0F) },
//                label = { Text("Custom target calories") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//            )
        }
    }
}