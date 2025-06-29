package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Centimeters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var conversionFactor by remember { mutableStateOf(0.01) }

    fun convert() {
        val input = inputValue.toDoubleOrNull() ?: 0.0
        val result = input * conversionFactor
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convert()
            },
            label = { Text("Enter value") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Input Unit Dropdown
            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = iExpanded,
                    onDismissRequest = { iExpanded = false }
                ) {
                    listOf("Centimeters", "Meters", "Feet", "Millimeters").forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                inputUnit = unit
                                conversionFactor = when (unit) {
                                    "Centimeters" -> 0.01
                                    "Meters" -> 1.0
                                    "Feet" -> 0.3048
                                    "Millimeters" -> 0.001
                                    else -> 1.0
                                }
                                iExpanded = false
                                convert()
                            }
                        )
                    }
                }
            }

            // Output Unit Dropdown
            Box {
                Button(onClick = { oExpanded = true }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = oExpanded,
                    onDismissRequest = { oExpanded = false }
                ) {
                    listOf("Centimeters", "Meters", "Feet", "Millimeters").forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                outputUnit = unit
                                oExpanded = false
                                convert()
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Result inside styled container like a button
        Box(
            modifier = Modifier
                .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Result: $outputValue $outputUnit",
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}