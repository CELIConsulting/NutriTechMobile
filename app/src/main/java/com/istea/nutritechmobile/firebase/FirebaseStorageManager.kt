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
    private val auth = firebaseAuthManager.getAuth()

    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImgFood(bytes: ByteArray) {

        val filePath = "${auth.currentUser!!.uid}-${
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        }.jpg"
        storage.getReference("users")
            .child(
                filePath
            )
            .putBytes(bytes)
            .addOnCompleteListener {
                Log.d(TAG, "Imagen enviada al storage")
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