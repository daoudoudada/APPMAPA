package com.example.mapa.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mapa.MyViewModel
import com.example.mapa.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(myviewModel: MyViewModel,navController: NavController){

    var message by remember { mutableStateOf("") }
    var showCorrect by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val goToNext  by myviewModel.goToNext.observeAsState(false)
    val correo by myviewModel.correo.observeAsState("")
    val contrasenya by myviewModel.contrasenya.observeAsState("")
    val nombre by myviewModel.nombre.observeAsState("")
    val apellidosState by myviewModel.apellidos.observeAsState("")
    val ConfirmarContrasenya by myviewModel.contrasenyaConfirmada.observeAsState("")

    if (goToNext) {
        navController.navigate(Routes.LoginScreen.route)
    }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(16.dp)) {
    OutlinedTextField(
        modifier = Modifier.size(180.dp, 60.dp),
        value = nombre,
        onValueChange = { myviewModel.setNombre(it) },
        label = { Text("nombre") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        )
    )
        Spacer(modifier = Modifier.width(10.dp))
    OutlinedTextField(
        modifier = Modifier.size(180.dp, 60.dp),
        value = apellidosState,
        onValueChange = {myviewModel.setApellidos(it) },
        label = { Text("apellidos") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        )
    )
}
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
        modifier=Modifier.size(350.dp,60.dp),
        value = correo,
        onValueChange = { myviewModel.setCorreo(it) },
        label = { Text("Correo electrónico") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Green,
            unfocusedBorderColor = Color.Black
        )
    )
        Spacer(modifier = Modifier.height(20.dp))

    OutlinedTextField(
        modifier=Modifier.size(350.dp,60.dp),
        value = contrasenya,
        onValueChange = { myviewModel.setcontrasenya(it) },
        label = { Text("Contraseña") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        )
    )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
        modifier=Modifier.size(350.dp,60.dp),
        value = ConfirmarContrasenya,
        onValueChange = { myviewModel.setConfirmarcontrasenya(it) },
        label = { Text("Confirmar Contraseña") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        )
    )
        Button(
                onClick = {
                    if (contrasenya.length < 6) {
                        showError = true
                        errorMessage="la contrasenya es muy corta "
                    } else if (contrasenya!=ConfirmarContrasenya) {
                        showError = true
                        errorMessage = "las dos contrasenyas no coinceden"
                    } else if (correo.contains("@") && correo.contains(".") && contrasenya==ConfirmarContrasenya && contrasenya.length >= 6 ){
                      showCorrect=true
                        message="todo correcto"
                        myviewModel.register(correo,contrasenya)
                    } else {
                        showError = true
                        errorMessage = "Por favor, ingrese un correo electrónico y una contraseña válidos."
                    }
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(104, 213, 82),

                ),
            shape = CutCornerShape(10),
            modifier = Modifier
                .size(250.dp, 80.dp)
                .padding(10.dp)
        ) {
            Text(text = "Registrarse")
        }
}
    if (showError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
    }
    if (showCorrect) {
        Text(
            text = message,
            color = Color.Green,
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
    }

}
