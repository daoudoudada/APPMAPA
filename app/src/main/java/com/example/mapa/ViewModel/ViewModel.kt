package com.example.mapa

import android.graphics.Bitmap
import com.example.mapa.Model.Marcador
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyViewModel : ViewModel() {

    private val repository = Repository()


    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre


    private val _apellidos = MutableLiveData<String>()
    val apellidos: LiveData<String> = _apellidos

    private val _cameraPermissionGranted =MutableLiveData(false)
    val cameraPermissionGranted = _cameraPermissionGranted

    private val _shouldShowPermissionRationale = MutableLiveData(false)
    val shouldShowPermissionRationale = _shouldShowPermissionRationale

    private val _showPermissionDenied = MutableLiveData(false)
    val showPermissionDenied = _showPermissionDenied

    private val _title = MutableLiveData("")
    val title = _title
    private val _tempDesc = MutableLiveData("")
    val tempDesc = _tempDesc

    private val _selectedPosition = MutableLiveData(LatLng(0.0, 0.0))
    val selectedPosition = _selectedPosition

   private val _actualUser = MutableLiveData<User>()
    val actualUser: LiveData<User> get() = _actualUser

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri = _imageUri

    private val _registrados = MutableLiveData(false)
    val registrados = _registrados

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _age = MutableLiveData<String>()
    val age: LiveData<String> get() = _age

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    fun setCameraPermissionGranted(granted: Boolean) {
        _cameraPermissionGranted.value = granted
    }
    private val _correo = MutableLiveData<String>()
    val correo = _correo

    private val _contrasenya = MutableLiveData<String>()
    val contrasenya = _contrasenya

    private val _contrasenyaConfirmada = MutableLiveData<String>()
    val contrasenyaConfirmada = _contrasenyaConfirmada

    fun setShouldShowPermissionRationale(should: Boolean) {
        _shouldShowPermissionRationale.value = should
    }
    fun setShowPermissionDenied (denied: Boolean) {
        _showPermissionDenied.value = denied
    }
    private val _markers = MutableLiveData<MutableList<Marcador>>()
    val markers: LiveData<MutableList<Marcador>> = _markers

    private val _listaLocalizacion = MutableLiveData<MutableList<Marcador>>(mutableListOf())
    val listaLocalizacion = _listaLocalizacion

    private val _geolocalizar = MutableLiveData(LatLng(0.0, 0.0))
    val geolocalizar = _geolocalizar



    fun getUsers() {
        repository.getUSers().addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Firestore error", error.message.toString())
                return@addSnapshotListener
            }
            var tempList = mutableListOf<User>()
            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    val newUser = dc.document.toObject(User::class.java)
                    newUser.userId = dc.document.id
                    tempList.add(newUser)

                }
            }

            _userList.value = tempList
        }
    }
    fun setNombre(Nombre: String) {
        _nombre.value = Nombre
    }
    fun setTitle(titulo:String){
        _title.value=titulo
    }
    fun setDescripcion(discrepcion:String){
        _tempDesc.value=discrepcion
    }
    private var position = LatLng(41.4534265, 2.1837151)
    private val _capturedPhoto =  mutableStateOf<Bitmap?>(null)

    fun savePhoto(photo: Bitmap) {
        _capturedPhoto.value = photo
    }
    fun setApellidos(Apellidos: String) {
        _apellidos.value = Apellidos
    }
    fun nuevaPosicion(positionNueva: LatLng) {
        position = positionNueva
    }
    fun setCorreo(correo: String) {
        _correo.value = correo
    }

    fun setcontrasenya(contrasenya: String) {
        _contrasenya.value = contrasenya
    }
    fun setConfirmarcontrasenya(conficontrasenya: String) {
        _contrasenyaConfirmada.value = conficontrasenya
    }
    fun anyadirMarcador(marcador: Marcador){
        repository.addMarkador(marcador)
        getMarkers()
    }


    fun getMarkers() {
        repository.getMarkers().addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("FireStore error", error.message.toString())
                return@addSnapshotListener
            }
            val tempList = mutableListOf<Marcador>()
            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    val newMarker = dc.document.toObject(Marcador::class.java)
                    newMarker.markerId = dc.document.id
                    tempList.add(newMarker)
                }
            }
            _listaLocalizacion.value = tempList
        }
    }
    fun uploadImage(imageUri: Uri, marcador: Marcador) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("images/$fileName")
        storage.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                Log.i("IMAGE UPLOAD", "Image uploaded successfully")
                storage.downloadUrl.addOnSuccessListener { uri ->
                    Log.i("IMAGEN", uri.toString())
                    val nuevoMarcador = Marcador(
                        markerId = null,
                        uid = marcador.uid,
                        titulo = marcador.titulo,
                        latitud = marcador.latitud,
                        longitud = marcador.longitud,
                        type=marcador.type,
                        descripcion = marcador.descripcion,
                        imagen = uri.toString()
                    )
                    anyadirMarcador(nuevoMarcador)
                }
            }
            .addOnFailureListener { exception ->
                Log.i("IMAGE UPLOAD", "Image upload failed", exception)
                val nuevoMarcador = Marcador(
                    markerId = null,
                    uid = marcador.uid,
                    titulo = marcador.titulo,
                    latitud = marcador.latitud,
                    longitud = marcador.longitud,
                    type=marcador.type,
                    descripcion = marcador.descripcion,
                    imagen = null
                )
                anyadirMarcador(nuevoMarcador)
            }
    }

    private val auth = FirebaseAuth.getInstance()
    private val _userId = MutableLiveData<String>()
    val userId = _userId

    private val _loggedUser = MutableLiveData(false)
    val loggedUser = _loggedUser

    private val _goToNext = MutableLiveData(false)
    val goToNext = _goToNext

    // REGISTRAR
    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    _goToNext.value = true
                }else{
                    _goToNext.value = false
                    Log.d("Error","error creating user: ${task.result}")
                }
            }
            .addOnFailureListener {
                Log.d("hubo otro error", "error creating user: ${it.message}")
            }
    }
// iniciar session

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _userId.value = task.result?.user?.uid
                    _loggedUser.value = true
                } else {
                    _loggedUser.value = false
                    Log.d("Error", "Error logging in: ${task.exception?.message}")
                }
            }
            .addOnFailureListener{
                Log.d("hubo otro error Error", "Error logging in: ${it.message}")
            }

    }



// cerrar session
    fun logOut() {
        auth.signOut()
        _loggedUser.value = false
    }


  /*  fun getUser(userId:String){
        repository.getUser(userId).addSnapshotListener { value, error ->
            if (error!=null){
                Log.w("UserRepository","Listen failed",error)
                return@addSnapshotListener
            }
            if (value!=null && value.exists()){
                val user=value.toObject(User::class.java)
                if (user!=null){
                    user.userId=userId
                }
                _actualUser.value=user
                _userName.value=_actualUser.value!!.userName
                _age.value=_actualUser.value!!.age.toString()
            } else{
                Log.d("UserRepository","Current data:null")
            }
        }
    }*/
}
