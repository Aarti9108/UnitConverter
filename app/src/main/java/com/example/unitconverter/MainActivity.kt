package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

// Main activity of the app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables drawing content under system bars
        enableEdgeToEdge()

        // Sets the main content of the app using Compose
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()  // Call the UI Composable
                }
            }
        }
    }
}

// Main UnitConverter UI
@Composable
fun UnitConverter() {
    // User input value (e.g., 100)
    var inputValue by remember { mutableStateOf("") }

    // Converted output value (e.g., 1.0)
    var outputValue by remember { mutableStateOf("") }

    // Currently selected input unit
    var inputUnit by remember { mutableStateOf("Centimeters") }

    // Currently selected output unit
    var outputUnit by remember { mutableStateOf("Meters") }

    // Boolean to show/hide input unit dropdown
    var iExpanded by remember { mutableStateOf(false) }

    // Boolean to show/hide output unit dropdown
    var oExpanded by remember { mutableStateOf(false) }

    // Conversion factor (e.g., 0.01 from cm to m)
    var conversionFactor by remember { mutableStateOf(0.01) }

    // Function to perform unit conversion
    fun convert() {
        val input = inputValue.toDoubleOrNull() ?: 0.0  // Safe parse to double
        val result = input * conversionFactor           // Simple multiplication
        outputValue = result.toString()                 // Convert back to string for display
    }

    // UI layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),  // Padding around edges
        verticalArrangement = Arrangement.Center,       // Centered vertically
        horizontalAlignment = Alignment.CenterHorizontally // Centered horizontally
    ) {
        Text("Unit Converter", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Input field for numeric value
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convert()  // Update result live
            },
            label = { Text("Enter value") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Row with two dropdowns: input unit & output unit
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // -------- INPUT UNIT DROPDOWN --------
            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }

                DropdownMenu(
                    expanded = iExpanded,
                    onDismissRequest = { iExpanded = false }
                ) {
                    // List of input units
                    listOf("Centimeters", "Meters", "Feet", "Millimeters").forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                inputUnit = unit
                                // Update conversion factor (assumes conversion to meters)
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

            // -------- OUTPUT UNIT DROPDOWN --------
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

        Spacer(modifier = Modifier.height(16.dp))

        // Display the result
        Text("Result: $outputValue $outputUnit", style = MaterialTheme.typography.bodyLarge)
    }
}

// Preview in Android Studio
@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}