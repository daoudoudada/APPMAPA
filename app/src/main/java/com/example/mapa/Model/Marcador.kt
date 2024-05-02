package com.example.mapa.Model

data class Marcador(
    var markerId: String? = null,
    var uid: String,
    var titulo: String,
    var latitud: Double,
    var type:String,
    var longitud: Double,
    var descripcion:String? = null,
    var imagen: String?,
) {
    constructor(): this(null, "", "", 0.0,"",0.0,"",null)
}
