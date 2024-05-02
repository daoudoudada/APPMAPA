package com.example.mapa.View

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mapa.MyViewModel
import com.example.mapa.R
import com.example.mapa.Routes

@Composable
fun anyadirMarcador( navController: NavController,MyViewModel: MyViewModel) {
    val titulo by MyViewModel.title.observeAsState("")
    val description by MyViewModel.tempDesc.observeAsState("")
    val context = LocalContext.current
    val img: Bitmap? = ContextCompat.getDrawable(context, R.drawable.logo)?.toBitmap()
    var bitmap by remember { mutableStateOf(img) }

    val launchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = it.let { it1 ->
                        ImageDecoder.createSource(context.contentResolver, it1)
                    }
                    source.let { it1 ->
                        ImageDecoder.decodeBitmap(it1)
                    }
                }
            }
        }
    )
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = titulo,
            onValueChange = { MyViewModel.setTitle(it) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = description,
            onValueChange = { MyViewModel.setDescripcion(it) },
            label = { Text("Snippet") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = { navController.navigate(Routes.TakePhotoScreen.route )}) {
                Icon(
                    imageVector = Icons.Rounded.CameraAlt,
                    contentDescription = "Icon Example",
                    modifier = Modifier
                        .size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            IconButton(
                onClick = {
                    launchImage.launch("image/*")
                }
            ) {
                Icon(imageVector = Icons.Default.Photo,
                    contentDescription = "galeria",
                    modifier = Modifier
                    .size(60.dp))
            }
        }
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick ={/*logica para añadir marcadores*/},
                shape = RoundedCornerShape(3.dp),
            colors = ButtonDefaults.buttonColors(containerColor= Color.Blue),
            modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Guardar")
            }
        }
    }

