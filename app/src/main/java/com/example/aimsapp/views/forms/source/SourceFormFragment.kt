package com.example.aimsapp.views.forms.source

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.databinding.SourcePostFormBinding
import com.example.aimsapp.databinding.SourcePreFormBinding
import com.example.aimsapp.views.currentTrip.CurrentTripViewModel
import com.example.aimsapp.views.currentTrip.CurrentTripViewModelFactory
import com.example.aimsapp.views.forms.FormViewModel
import com.example.aimsapp.views.forms.SignaturePad
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
@RequiresApi(Build.VERSION_CODES.O)
class SourceFormFragment(num: Int, wayPoint: WayPoint): Fragment() {

    private lateinit var binding1: SourcePreFormBinding
    private lateinit var binding2: SourcePostFormBinding
    private lateinit var viewModel: SourceViewModel
    private var CAMERA_REQUEST_CODE = 0
    private val no = num
    private val wayPoint = wayPoint
    private lateinit var frag:SourceFormDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireActivity().application
        val viewModelFactory = SourceViewModelFactory(application)
        frag = parentFragment as SourceFormDialog
        if (no == 0){
            binding1 = DataBindingUtil.inflate(inflater,R.layout.source_pre_form,container,false)

            binding1.apply {

                endButton.isEnabled = false
                startButton.setOnClickListener {
                    setStartDateTime()
                }
                endButton.setOnClickListener {
                    setEndDateTime()
                }
            }
        }
        else{
            binding2 = DataBindingUtil.inflate(inflater,R.layout.source_post_form,container,false)
            binding2.lifecycleOwner = frag
            viewModel = ViewModelProvider(frag, viewModelFactory).get(SourceViewModel::class.java)
            binding2.viewModel = viewModel

            binding2.apply {
                submitButton.setOnClickListener {
                    submitHandler()
                }
                uploadButton.setOnClickListener {
                    CAMERA_REQUEST_CODE = 200
                    checkPermissionAndOpenCamera(CAMERA_REQUEST_CODE)
                }
                signatureButton.setOnClickListener {
                    val dialog = SignaturePad(0)
                    dialog.show(childFragmentManager,"SignaturePad")
                }
            }

            Log.i("CloseForm","Form Created 2")

            return binding2.root
        }


        binding1.lifecycleOwner = frag
        viewModel = ViewModelProvider(frag, viewModelFactory).get(SourceViewModel::class.java)
        viewModel.startForm(wayPoint)
        binding1.viewModel = viewModel

        viewModel.startDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.isEmpty()){
                    binding1.startButton.isEnabled = true
                }
                else{
                    binding1.startButton.isEnabled = false
                    if(viewModel.endDate.value?.isEmpty() == true){
                        binding1.endButton.isEnabled = true
                    }
                }
            }
        })

        Log.i("CloseForm","Form Created 1 ${binding1.startDateEdit.text}")

        return binding1.root
    }


    private fun setStartDateTime() {
        val startDateTime  = LocalDateTime.now()
        val date = startDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        val time = startDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))
        binding1.apply {
            startDateEdit.setText(date)
            startTimeEdit.setText(time)
            startButton.isEnabled = false
            endButton.isEnabled = true
        }
    }

    private fun setEndDateTime() {
        val endDateTime  = LocalDateTime.now()
        val date = endDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        val time = endDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))
        binding1.apply {
            endDateEdit.setText(date)
            endTimeEdit.setText(time)
            endButton.isEnabled = false
        }
    }



    private fun submitHandler(){
        viewModel.startForm(wayPoint)
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        if(formIsEmpty()){
            alertDialogBuilder.setTitle("Please fill out the form")
            alertDialogBuilder.setMessage("You must fill out all the * field!!")
            alertDialogBuilder.setCancelable(true)
            alertDialogBuilder.setNegativeButton("OK") { _, _ ->
            }
        }
        else{
            alertDialogBuilder.setTitle("Form Sent to Dispatcher")
            alertDialogBuilder.setMessage("Demo")
            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.setPositiveButton("Done"){_,_ ->
                wayPoint.completed = true
                viewModel.updatePoint(wayPoint)

                frag.dismiss()
            }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun formIsEmpty(): Boolean{
        if(viewModel.productType.value.toString().isEmpty() || viewModel.startDate.value.toString().isEmpty() || viewModel.startTime.value.toString().isEmpty()
            || viewModel.endDate.value.toString().isEmpty() || viewModel.endTime.value.toString().isEmpty()
            || viewModel.grossGallons.value.toString().isEmpty() || viewModel.netGallons.value.toString().isEmpty()
            || viewModel.billOfLading.value.toString().isEmpty()){

            return true
        }
        if(binding2.signatureView.drawable == null){
            return true
        }
        return false
    }

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
            val photo = binding2.imageView
            val bitmap = data.extras?.get("data") as Bitmap
            photo.setImageBitmap(bitmap)
            photo.requestLayout()
            photo.layoutParams.width = bitmap.width + 200
            photo.layoutParams.height = bitmap.height + 200
            Toast.makeText(requireContext(), "${bitmap.height}, ${bitmap.width}", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateSignatureDisplay(bitmap: Bitmap){
        binding2.signatureView.setImageBitmap(bitmap)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveForm()
        Log.i("CloseForm","pause")

    }

}