package com.example.mapa

sealed class Routes(val route: String) {

    object SplashScreen:Routes("splash_screen")

    object anyadirMarcador:Routes("anyadirMarcador")

    object GalleryScreen:Routes("GalleryScreen")

    object MyDrawer:Routes("myDrawe")

    object CameraScreen:Routes("CameraScreen")

    object TakePhotoScreen:Routes("TakePhotoScreen")

    object LoginScreen:Routes("Login")

    object registerScreen:Routes("register")

}
