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

class SiteFormFragment : Fragment(){

    private lateinit var binding: FragmentSourceFormBinding
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
        alertDialogBuilder.setTitle("Form Sent to Dispatcher")
        alertDialogBuilder.setMessage("Choose Mode")
        alertDialogBuilder.setMessage("whats up")

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}