package com.example.aimsapp.views.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.aimsapp.R
import com.example.aimsapp.activities.SplashScreen
import com.example.aimsapp.databinding.FragmentProfileBinding
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private var onBreak = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("tripStatus shared prefs", Context.MODE_PRIVATE)
        onBreak = sharedPreferences.getBoolean("break", false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.breakCard.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            if(onBreak){
                alertDialogBuilder.apply {
                    setTitle("You are on a break!")
                    setMessage("Do you want to end the break?")
                    setNegativeButton("No"){_,_ ->
                    }
                    setPositiveButton("Yes"){_,_->
                        sharedPreferences.edit().putBoolean("break",false).apply()
                        onBreak = false
                    }
                }
                alertDialogBuilder.create().show()
            }
            else{
                alertDialogBuilder.apply {
                    setTitle("Looks like you need a break")
                    setMessage("Do you want to take a break?")
                    setNegativeButton("No"){_,_ ->
                    }
                    setPositiveButton("Yes"){_,_->
                        sharedPreferences.edit().putBoolean("break",true).apply()
                        onBreak = true
                    }
                }
                alertDialogBuilder.create().show()
            }
        }
        binding.helpCard.setOnClickListener {
            showHelp()
        }
        binding.aboutCard.setOnClickListener {
            showAbout()
        }
        binding.logoutCard.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("login shared prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("loggedIn", false).apply()
            val i = Intent(requireContext(),SplashScreen::class.java)
            requireActivity().startActivity(i)
            requireActivity().finish()
        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.trips.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                viewModel.updateValues(it[0])
                var completed = 0
                for (trip in it){
                    if(trip.completed){
                        completed++
                    }
                }
                viewModel.updateCompleted(completed)
            }
        })
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