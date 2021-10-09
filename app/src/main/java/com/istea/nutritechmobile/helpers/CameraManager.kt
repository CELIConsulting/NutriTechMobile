package com.istea.nutritechmobile.helpers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.istea.nutritechmobile.firebase.FirebaseAuthManager
import com.istea.nutritechmobile.helpers.extensions.stringFromDate
import java.io.File
import java.util.*

private const val TAG = "CameraManager"

class CameraManager(
    private var activity: Activity,
    private var imageView: ImageView,
) {
    private val REQUEST_TAKE_PHOTO = 1
    private val AUTHORITY = "com.istea.nutritechmobile"
    private val permissionCamera = android.Manifest.permission.CAMERA
    private val permissionWriteStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val permissionReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE

    private var pathImageFile = ""
    private var urlFotoActual = " "

    fun takePhoto() {
        requestPermission()
    }

    fun cleanPhoto() {
        imageView.setImageResource(android.R.color.transparent)
    }

    private fun requestPermission() {
        ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionCamera)

        activity.requestPermissions(
            arrayOf(
                permissionCamera,
                permissionWriteStorage,
                permissionReadStorage
            ), REQUEST_TAKE_PHOTO
        )
    }

    fun requestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchTakePictureIntent()
                } else {
                    Toast.makeText(
                        activity.applicationContext,
                        "No diste permiso para acceder a la camara y almacenamiento",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(activity.packageManager) != null) {

            var imageFile: File? = null
            var fileRoute: String? = null
            val result = createImageFile()
            imageFile = result["imagen"] as File?
            fileRoute = result["filename"] as String?

            if (imageFile != null) {
                val urlFoto =
                    FileProvider.getUriForFile(activity.applicationContext, AUTHORITY, imageFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, urlFoto)
                intent.putExtra("filenameImage", fileRoute)
                activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO)
            }
        }
    }


    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    //obtener imagen
                    Log.d(TAG, "obtener imagen")
                    showBitmap(urlFotoActual)

                } else {
                    Log.d(TAG, "Canceled capture")
                }
            }
        }
    }

    private fun showBitmap(url: String) {
        val uri = Uri.parse(url)
        val stream = activity.contentResolver.openInputStream(uri)
        val imageBitmap = BitmapFactory.decodeStream(stream)
        imageView.setImageBitmap(imageBitmap)
    }


    private fun createImageFile(): HashMap<String, Any> {
        val filename = "${FirebaseAuthManager().getAuthUser()}_{Date().stringFromDate()}"
        val directorio = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imagen = File.createTempFile(filename, ".jpg", directorio)
        urlFotoActual = "file://" + imagen.absolutePath //me regresa toda la url de la imagen
        pathImageFile = imagen.absolutePath
        return hashMapOf(Pair("filename", filename), Pair("image", imagen))
    }

    fun getPath(): String {
        return pathImageFile
    }
}