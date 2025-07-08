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
import androidx.compose.ui.text.input.KeyboardType
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
    val units = listOf("Centimeters", "Meters", "Feet", "Millimeters")

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf(units[0]) }
    var outputUnit by remember { mutableStateOf(units[1]) }

    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    fun convert() {
        val input = inputValue.toDoubleOrNull()
        if (input == null) {
            outputValue = "Invalid input"
            return
        }

        val meters = toMeters(input, inputUnit)
        val result = fromMeters(meters, outputUnit)
        outputValue = String.format("%.4f", result)
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
            label = { Text("Enter value") },
            keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
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
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Input Unit Dropdown")
                }
                DropdownMenu(
                    expanded = iExpanded,
                    onDismissRequest = { iExpanded = false }
                ) {
                    units.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                inputUnit = unit
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
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Output Unit Dropdown")
                }
                DropdownMenu(
                    expanded = oExpanded,
                    onDismissRequest = { oExpanded = false }
                ) {
                    units.forEach { unit ->
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

        // Output display
        Box(
            modifier = Modifier
                .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (outputValue.isNotBlank())
                    "Result: $outputValue $outputUnit"
                else
                    "Result will appear here",
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

// Helper functions
fun toMeters(value: Double, unit: String): Double {
    return when (unit) {
        "Centimeters" -> value * 0.01
        "Meters" -> value
        "Feet" -> value * 0.3048
        "Millimeters" -> value * 0.001
        else -> value
    }
}

fun fromMeters(value: Double, unit: String): Double {
    return when (unit) {
        "Centimeters" -> value / 0.01
        "Meters" -> value
        "Feet" -> value / 0.3048
        "Millimeters" -> value / 0.001
        else -> value
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}
