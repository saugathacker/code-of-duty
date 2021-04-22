package com.example.aimsapp.views.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentProfileBinding
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
            Toast.makeText(requireContext(), "Help has not been implemented", Toast.LENGTH_SHORT).show()
        }
        binding.aboutCard.setOnClickListener {
            Toast.makeText(requireContext(), "About has not been implemented", Toast.LENGTH_SHORT).show()
        }
        binding.logoutCard.setOnClickListener {
            Toast.makeText(requireContext(), "Logout has not been implemented", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}