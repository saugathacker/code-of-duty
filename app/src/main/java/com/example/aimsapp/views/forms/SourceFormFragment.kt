package com.example.aimsapp.views.forms

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentSourceFormBinding

class SourceFormFragment: Fragment() {

    private lateinit var binding: FragmentSourceFormBinding
    private lateinit var viewModel: FormViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_source_form,container,false)
        viewModel = ViewModelProvider(this).get(FormViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.Submit.setOnClickListener {
            submitHandler()
        }

        return binding.root
    }

    private fun submitHandler(){
        val alertDialogBuilder =
            AlertDialog.Builder(requireActivity())
        if (formIsEmpty()){
            alertDialogBuilder.setTitle("Please fill out the form before submitting")
        }
        else{
            viewModel.setValues(binding.productType.text.toString(),binding.startDateTime.text.toString(), binding.endDateTime.text.toString(), binding.grossGallons.text.toString().toDouble(), binding.netGallons.text.toString().toDouble(), 0.0,0.0, binding.billOfLadingNumber.text.toString())
            alertDialogBuilder.setTitle("Form Sent to Dispatcher")
            alertDialogBuilder.setMessage("Product Type: ${viewModel.productType.value} \nStart Date and Time: ${viewModel.startTimeAndDate.value} \nEnd Date and Time: ${viewModel.endTimeAndDate.value} \n" +
                    "Gross Gallons: ${viewModel.grossGallons.value.toString()} \n" +
                    "Net Gallons: ${viewModel.netGallons.value.toString()} \n" +
                    "Bill of Lading: ${viewModel.notes.value}")
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun formIsEmpty(): Boolean{
        if (binding.productType.text.toString() == "" || binding.startDateTime.text.toString() == ""
            || binding.endDateTime.text.toString() == "" || binding.grossGallons.text.toString() == ""
            || binding.netGallons.text.toString() == "" || binding.billOfLadingNumber.text.toString() == ""){
            return true
        }
        return false
    }
}