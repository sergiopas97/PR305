package com.example.pr305

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pr305.ui.theme.PR305Theme
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR305Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modificador: Modifier = Modifier) {
    // Estado para la entrada de nota del examen
    var numInput by remember { mutableStateOf("") }

    // Estado para el mensaje que se mostrará
    var mensaje by remember { mutableStateOf("") }

    // Columna que organiza los elementos de la interfaz de usuario verticalmente
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Texto que indica al usuario que introduzca la nota del examen
        Text(text = "Introduce la nota del examen:")

        // Campo de texto para la entrada de la nota
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = numInput,
            onValueChange = {
                // Limitar la longitud de la entrada a 4 caracteres
                if (it.length <= 4) {
                    numInput = it
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        // Botón elevado para comprobar la nota del examen
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 4.dp),
            onClick = {
                val userInput = numInput.toDoubleOrNull()

                // Evaluar la nota y asignar un mensaje correspondiente
                if (userInput != null) {
                    when {
                        userInput in 0.0..4.9 -> mensaje = "Suspenso."
                        userInput in 5.0..6.9 -> mensaje = "Aprobado."
                        userInput in 7.0..10.0 -> mensaje = "Excelente."
                        else -> mensaje = "Dato no válido"
                    }
                }
            }
        ) {
            Text(
                text = "Comprobar",
            )
        }

        // Mostrar el mensaje durante 8 segundos usando LaunchedEffect
        LaunchedEffect(mensaje){
            delay(8000)
            mensaje = ""
        }

        // Mostrar el mensaje si no está vacío
        if (mensaje.isNotEmpty()) {
            Text(text = mensaje)
        }
    }
}

// Composable para la vista previa de la interfaz de usuario
@Preview(showBackground = true, widthDp = 360)
@Composable
fun MyPreview() {
    PR305Theme(dynamicColor = false) {
        MainScreen(Modifier.fillMaxSize())
    }
}