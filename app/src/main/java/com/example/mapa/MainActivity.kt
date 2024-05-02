package com.example.mapa

import GalleryScreen
import com.example.mapa.Model.Marcador
import MyDrawer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt

import androidx.compose.material.icons.rounded.LocalGasStation
import androidx.compose.material.icons.rounded.Park
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.IconButton
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.View.CameraScreen
import com.example.View.TakePhotoScreen
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapa.View.LoginScreen
import com.example.mapa.View.RegisterScreen
import com.example.mapa.View.SplashScreen
import com.example.mapa.View.anyadirMarcador
import com.example.mapa.ui.theme.MAPATheme

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myViewModel by viewModels<MyViewModel>()

        setContent {
            MAPATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.SplashScreen.route
                    ) {
                        composable(Routes.SplashScreen.route) {
                            SplashScreen(navController)
                        }
                        composable(Routes.LoginScreen.route) {
                            LoginScreen(myViewModel,navController)
                        }
                        composable(Routes.registerScreen.route){
                            RegisterScreen(myViewModel, navController)
                        }
                        composable(Routes.MyDrawer.route) {
                            MyDrawer(navController,myViewModel)
                        }
                        composable(Routes.CameraScreen.route) {
                            CameraScreen(navController, myViewModel)
                        }
                        composable(Routes.TakePhotoScreen.route){
                            TakePhotoScreen(navController, myViewModel)
                        }
                        composable(Routes.GalleryScreen.route){
                            GalleryScreen(navController, myViewModel)
                        }
                        composable(Routes.anyadirMarcador.route){
                            anyadirMarcador(navController, myViewModel)
                        }
                    }
                }
            }
        }
    }
}




