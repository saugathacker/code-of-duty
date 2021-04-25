package com.example.aimsapp.views.profile

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentProfileBinding
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.settingsCard.setOnClickListener {
            Toast.makeText(requireContext(), "Settings has not been implemented", Toast.LENGTH_SHORT).show()
        }
        binding.helpCard.setOnClickListener {
            showHelp()
        }
        binding.aboutCard.setOnClickListener {
            showAbout()
        }
        binding.logoutCard.setOnClickListener {
            Toast.makeText(requireContext(), "Logout has not been implemented", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showHelp() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_help)
        dialog.setCancelable(true)
        dialog.findViewById<MaterialButton>(R.id.done).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showAbout() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_about)
        dialog.setCancelable(true)
        dialog.findViewById<MaterialButton>(R.id.done).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}