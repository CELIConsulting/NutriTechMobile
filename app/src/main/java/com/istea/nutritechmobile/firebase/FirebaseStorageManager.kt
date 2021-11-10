package com.istea.nutritechmobile.firebase

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.storage.FirebaseStorage
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val TAG = "FirebaseStorageManager"

//TODO: se usa el context en algun lado
class FirebaseStorageManager(context: Context, firebaseAuthManager: FirebaseAuthManager) {
    private val storage = FirebaseStorage.getInstance()
    private val authEmail = firebaseAuthManager.getAuthEmail()
    private val storageRef = storage.getReference("users")

//    @RequiresApi(Build.VERSION_CODES.O)
//    private val filePath = "${authEmail}-${
//        DateTimeFormatter
//            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
//            .withZone(ZoneOffset.UTC)
//            .format(Instant.now())
//    }.jpg"

    //Path example: users/paciente17@gmail.com/foodUpload/paciente17@gmail.com_timestamp.jpg
    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImgFood(bytes: ByteArray, fileName: String) {
        storageRef
            .child(authEmail)
            .child("foodUpload")
            .child("${fileName}.jpg")
            .putBytes(bytes)
            .addOnCompleteListener {
                Log.d(TAG, "Foto de la comida enviada al storage")
            }
            .addOnSuccessListener {
                // TODO: retornar la url de la imagen que se va a subir y agregarla en algun lado de firebase
                Log.d(TAG, "URL de la foto de la imagen descargada")
            }
            .addOnFailureListener {
                Log.d(TAG, "Error al enviar la imagen: ${it.message.toString()}")
            }
    }

    //Path example: users/paciente17@gmail.com/bodyUpload/paciente17@gmail.com_timestamp.jpg
    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImgBody(bytes: ByteArray,fileName: String) {
        storageRef
            .child(authEmail)
            .child("bodyUpload")
            .child("${fileName}.jpg")
            .putBytes(bytes)
            .addOnCompleteListener {
                Log.d(TAG, "Foto corporal enviada al storage")
            }
            .addOnSuccessListener {
                // TODO: retornar la url de la imagen que se va a subir y agregarla en algun lado de firebase
                Log.d(TAG, "URL de la foto de la imagen descargada")
            }
            .addOnFailureListener {
                Log.d(TAG, "Error al enviar la imagen: ${it.message.toString()}")
            }
    }
}