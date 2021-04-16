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
import com.example.aimsapp.databinding.FragmentSiteFormBinding

class SiteFormFragment : Fragment(){

    private lateinit var binding: FragmentSiteFormBinding
    private lateinit var viewModel: FormViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_site_form,container,false)
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
        if(formIsEmpty()){
            alertDialogBuilder.setTitle("Please fill out the form before submitting")
        }
        else{
            viewModel.setValues(binding.productType.text.toString(),
                binding.dropDateTime.text.toString(),
                binding.dropDateTime.text.toString(),
                binding.grossGallonsDropped.text.toString().toDouble(),
                binding.netGallonsDropped.text.toString().toDouble(),
                binding.stickReadingBefore.text.toString().toDouble(),
                binding.stickReadingAfter.text.toString().toDouble(),
                "")
            alertDialogBuilder.setTitle("Form Sent to Dispatcher")
            alertDialogBuilder.setMessage("Product Type: ${viewModel.productType.value} \nStart Date and Time: ${viewModel.startTimeAndDate.value} \nEnd Date and Time: ${viewModel.endTimeAndDate.value} \n" +
                    "Gross Gallons: ${viewModel.grossGallons.value.toString()} \n" +
                    "Net Gallons: ${viewModel.netGallons.value.toString()} \n" +
                    "Stick Reading Before: ${viewModel.initialFuelReading.value} \n" +
                    "Stick Reading After: ${viewModel.finalFuelReading.value.toString()}")
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun formIsEmpty(): Boolean{
        if (binding.productType.text.toString() == "" || binding.dropDateTime.text.toString() == ""
            || binding.grossGallonsDropped.text.toString() == "" || binding.netGallonsDropped.text.toString() == ""
            || binding.stickReadingBefore.text.toString() == "" || binding.stickReadingAfter.text.toString() == ""){
            return true
        }
        return false
    }

}