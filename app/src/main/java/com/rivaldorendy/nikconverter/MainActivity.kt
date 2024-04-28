package com.rivaldorendy.nikconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rivaldorendy.nik_converter.NIKConverter
import com.rivaldorendy.nik_converter.NIKData
import com.rivaldorendy.nikconverter.ui.theme.NIKConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NIKConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConverterScreen()
                }
            }
        }
    }
}

@Composable
fun ConverterScreen() {
    val context = LocalContext.current

    var nik by remember { mutableStateOf("") }
    var nikData by remember {
        mutableStateOf(
            NIKData(
                province = "",
                city = "",
                district = "",
                districtPostalCode = "",
                gender = "",
                birthDate = 0,
                birthMonth = "",
                birthYear = 0,
                birthDay = "",
                birthdayCountdown = "",
                age = "",
                zodiacSign = "",
                chineseZodiac = "",
                uniqueCode = ""
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nik,
            onValueChange = { nik = it },
            label = { Text("Enter NIK") }
        )

        Text(
            text = "Province: ${nikData.province}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "City: ${nikData.city}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "District: ${nikData.district}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Postal Code: ${nikData.districtPostalCode}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Gender: ${nikData.gender}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Birth Date: ${nikData.birthDay} ${nikData.birthDate}/${nikData.birthMonth}/${nikData.birthYear}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Age: ${nikData.age}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Zodiac Sign: ${nikData.zodiacSign}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Chinese Zodiac: ${nikData.chineseZodiac}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Unique Code: ${nikData.uniqueCode}",
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = {
                if(NIKConverter().convert(nik, context) != null){
                    nikData = NIKConverter().convert(nik, context)!!
                }
                Toast.makeText(context, "invalid NIK", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = "Convert NIK")
        }
    }
}
