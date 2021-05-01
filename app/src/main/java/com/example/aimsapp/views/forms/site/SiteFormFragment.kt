package com.example.aimsapp.views.forms.site

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.R
import com.example.aimsapp.databinding.SitePostFormBinding
import com.example.aimsapp.databinding.SitePreFormBinding
import com.example.aimsapp.views.forms.FormViewModel
import com.example.aimsapp.views.forms.source.SourceViewModelFactory

class SiteFormFragment(num: Int) : Fragment(){

    private lateinit var binding1: SitePreFormBinding
    private lateinit var binding2: SitePostFormBinding
    private lateinit var viewModel: FormViewModel
    private val no = num

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireActivity().application
        val viewModelFactory = SiteViewModelFactory(application)
        if(no == 0){
            binding1 = DataBindingUtil.inflate(inflater,R.layout.site_pre_form,container,false)

        }else{
            binding2 = DataBindingUtil.inflate(inflater,R.layout.site_post_form,container, false)
            binding2.submitButton.setOnClickListener {
                submitHandler()
            }
            return binding2.root

        }
        viewModel = ViewModelProvider(this).get(FormViewModel::class.java)

        return binding1.root
    }

    private fun submitHandler(){

        val alertDialogBuilder =
            AlertDialog.Builder(requireActivity())
        //   viewModel.setValues(binding.productDropped.toString(),
        //       binding.dropDate.toString(),
        //      binding.dropTime.toString())
        //       binding.grossGallonsDropped.toString().toDouble(),
        //      binding.netGallonsDropped.toString().toDouble(),
        //      binding.initialMeterReading.toString().toDouble(),
        //     binding.finalMeterReading.toString().toDouble(),
        //     "")
        alertDialogBuilder.setTitle("Form Sent to Dispatcher")
        alertDialogBuilder.setMessage("Demo")

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

//    private fun formIsEmpty(): Boolean{
//        if (binding.productDropped.toString() == "" || binding.dropDate.toString() == ""
//   //         || binding.grossGallonsDropped.toString() == "" || binding.netGallonsDropped.toString() == ""
//    //       || binding.initialMeterReading.toString() == "" || binding.finalMeterReading.toString() == ""
//                  )
//           {
//            return true
//        }
//        return false
//    }

}