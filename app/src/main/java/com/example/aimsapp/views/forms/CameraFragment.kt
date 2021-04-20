package com.example.aimsapp.views.forms

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.aimsapp.R
import kotlinx.android.synthetic.main.fragment_source_form.*

class CameraFragment(contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {
private var CAMERA_REQUEST_CODE=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_source_form)
        submit_button.setOnClickListener{
    CAMERA_REQUEST_CODE=200
            checkPermissionAndOpenCamera(CAMERA_REQUEST_CODE)
        }
    }
private fun takePhotoFromCamera(requestCode:Int){
    val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, requestCode)
}
    private fun checkPermissionAndOpenCamera(requestCode: Int){
        if (ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.CAMERA)
        ==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 5)
        }
        else{
            takePhotoFromCamera(requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==5){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePhotoFromCamera(CAMERA_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200 && data != null) {
            image_View.setImageBitmap(data.extras?.get("data") as Bitmap)
        }


    }
}