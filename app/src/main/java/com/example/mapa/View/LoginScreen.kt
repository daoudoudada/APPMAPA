package com.example.mapa.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mapa.MyViewModel
import com.example.mapa.R
import com.example.mapa.Routes
import com.example.sergiitb_pr04_maps_app.model.UserPrefs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(myViewModel: MyViewModel, navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.SpaceAround) {

            val context = LocalContext.current
            val userPrefs = UserPrefs(context)
            var errorMessage by remember { mutableStateOf("") }
            var showError by remember { mutableStateOf(false) }
            val correo by myViewModel.correo.observeAsState("")
            val contrasenya by myViewModel.contrasenya.observeAsState("")
            val loggedUser by myViewModel.loggedUser.observeAsState(false)

            LaunchedEffect(loggedUser) {
                if (loggedUser == true) {
                    navController.navigate(Routes.MyDrawer.route)
                }
            }

            OutlinedTextField(
                modifier=Modifier.size(300.dp,60.dp),
                value = correo,
                onValueChange = { myViewModel.setCorreo(it) },
                label = { Text("Correo electrónico") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                modifier=Modifier.size(300.dp,60.dp),
                value = contrasenya,
                onValueChange = { myViewModel.setcontrasenya(it) },
                label = { Text("Contraseña") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                        onClick = {
                            if (correo.isNotEmpty() && contrasenya.isNotEmpty() && contrasenya.length>=6 && correo.contains("@") && correo.contains(".")){
                                myViewModel.login(correo, contrasenya)
                            } else {
                                showError=true
                                 errorMessage = "Por favor, ingrese un correo electrónico y una contraseña válidos."
                            }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                    ),
                    shape = CutCornerShape(7),
                    modifier = Modifier.size(300.dp, 60.dp)
                ) {
                    Text("Iniciar sesión", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                }

                Spacer(modifier = Modifier.height(25.dp))
                Text(text = "-----  O  -----", fontSize = 60.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {  myViewModel.login(correo,contrasenya) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray,

                        ),
                    shape = CutCornerShape(7),
                    modifier = Modifier.size(300.dp, 60.dp)
                ) {
                    Image( painter = painterResource(id = R.drawable.logo),
                         contentDescription ="logoGoogle" )
                    Text("Continuar con Google" ,color=Color.Black,fontWeight = FontWeight.ExtraBold, fontSize =18.sp)
                }

                Spacer(modifier = Modifier.height(25.dp))
                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.size(300.dp, 2.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {navController.navigate(Routes.registerScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(104, 213, 82),

                        ),
                    shape = CutCornerShape(10),
                    modifier = Modifier
                        .size(250.dp, 80.dp)
                        .padding(10.dp)
                ) {
                    Text("Crear Cuenta nueva", fontWeight = FontWeight.ExtraBold, fontSize =20.sp)
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
        }
    }


}
