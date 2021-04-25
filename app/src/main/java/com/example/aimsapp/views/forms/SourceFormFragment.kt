package com.example.aimsapp.views.forms

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentSourceFormBinding
import com.example.aimsapp.databinding.SourcePostFormBinding
import com.example.aimsapp.databinding.SourcePostFormBindingImpl
import com.example.aimsapp.databinding.SourcePreFormBinding

class SourceFormFragment(num: Int): Fragment() {

    private lateinit var binding1: SourcePreFormBinding
    private lateinit var binding2: SourcePostFormBinding
    private lateinit var viewModel: FormViewModel
    private var CAMERA_REQUEST_CODE = 0
    private val no = num

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (no == 0){
            binding1 = DataBindingUtil.inflate(inflater,R.layout.source_pre_form,container,false)

        }
        else{
            binding2 = DataBindingUtil.inflate(inflater,R.layout.source_post_form,container,false)

            binding2.submitButton.setOnClickListener {
                submitHandler()
            }

            binding2.uploadButton.setOnClickListener {
                CAMERA_REQUEST_CODE = 200
                checkPermissionAndOpenCamera(CAMERA_REQUEST_CODE)

            }

            return binding2.root
        }

        viewModel = ViewModelProvider(this).get(FormViewModel::class.java)

       return binding1.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private fun submitHandler(){
        val alertDialogBuilder =
            AlertDialog.Builder(requireActivity())
        //            viewModel.setValues(binding.productCode.toString(),binding.startDate.toString(), binding.startTime.toString(), binding.endDate.toString().toDouble(),binding.endTime.toString().toDouble(),binding.grossGallons.toString().toDouble(), binding.netGallons.toString().toDouble(), 0.0,0.0, binding.billOfLading.toString())
        alertDialogBuilder.setTitle("Form Sent to Dispatcher")
        alertDialogBuilder.setMessage("Demo")

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

//    private fun formIsEmpty(): Boolean{
//        if (binding.productCode.toString() == "" || binding.startDate.toString() == ""
//            || binding.endDate.toString() == "" || binding.startTime.toString() == ""|| binding.endTime.toString() == ""|| binding.grossGallons.toString() == ""
//            || binding.netGallons.toString() == "" || binding.billOfLading.toString() == ""){
//            return true
//        }
//        return false
//    }

    private fun takePhotoFromCamera(requestCode:Int){
        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, requestCode)
    }
    private fun checkPermissionAndOpenCamera(requestCode: Int){
        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA)
            ==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 5)
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
            binding2.imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
    }
}