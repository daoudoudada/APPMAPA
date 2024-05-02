package com.example.mapa

import com.example.mapa.Model.Marcador
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Repository {
    private val database= FirebaseFirestore.getInstance()

    fun addMarkador(marker: Marcador){
        database.collection("markers")
            .add(
                hashMapOf(
                    "title" to marker.titulo,
                    "uid" to marker.uid,
                    "latitude" to marker.latitud,
                    "longitude" to marker.longitud,
                    "description" to marker.descripcion,
                    "image" to marker.imagen
                )
            )
    }
    fun editMarkador(editedMarker : Marcador){
        database.collection("markers").document(editedMarker.markerId!!).set(
            hashMapOf(
                "titulo" to editedMarker.titulo,
                "latitud" to editedMarker.latitud,
                "longitud" to editedMarker.longitud,
                "descripcion" to editedMarker.descripcion,
                "imagen" to editedMarker.imagen
            )
        )
    }

    fun deleteMarker(markerID : String){
        database.collection("users").document(markerID).delete()
    }

    fun getMarkers() : CollectionReference {
        return database.collection("markers")
    }

    fun getMarker(userMarker : String) : DocumentReference{
        return database.collection("marker").document(userMarker)
    }
    fun addUser(user:User){
        database.collection("users")
            .add(
                hashMapOf(
                    "userName" to user.userName,
                    "age" to user.age,
                    "profilePicture" to user.profilePicture
                )
            )
    }
    fun editUser(editedUser:User){
        database.collection("users").document(editedUser.userId!!)
            .set(
                hashMapOf(
                    "userName" to editedUser.userName,
                    "age" to editedUser.age,
                    "profilePicture" to editedUser.profilePicture
                )
            )
    }
    fun deleteUser(userId:String){
        database.collection("users").document(userId).delete()
    }
    fun getUSers():CollectionReference{
        return database.collection("users")
    }
    fun getUser(userId:String):DocumentReference{
        return database.collection("users").document(userId)
    }

}
