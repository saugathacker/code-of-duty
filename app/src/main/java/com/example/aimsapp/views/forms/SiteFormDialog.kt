package com.example.aimsapp.views.forms

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aimsapp.R
import com.example.aimsapp.databinding.DialogSiteFormBinding
import com.example.aimsapp.databinding.DialogSourceFormBinding

class SiteFormDialog: DialogFragment() {

    private var current_step = 1
    private lateinit var binding: DialogSiteFormBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext(), R.style.DialogTheme)
        dialog.setContentView(binding.root)

//        return AlertDialog.Builder(activity, R.style.DialogTheme)
//            .setView(binding.root)
//            .create()
        return dialog
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_site_form,
            null,
            false
        )
        initComponent()
        val pagerAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = pagerAdapter

        toggleButtons()

    }

    private fun toggleButtons() {
        if (current_step == 1) {
            binding.lytBack.visibility = View.GONE
            binding.lytNext.visibility = View.VISIBLE
        } else {
            binding.lytNext.visibility = View.GONE
            binding.lytBack.visibility = View.VISIBLE
        }
    }

    private fun initComponent() {
        binding.lytBack.setOnClickListener {
            backStep(current_step)
            toggleButtons()

        }
        binding.lytNext.setOnClickListener {
            nextStep(current_step)
            toggleButtons()
        }

    }

    private fun nextStep(progress: Int) {
        var progress = progress
        if (progress < MAX_STEP) {
            progress++
            current_step = progress
            binding.viewPager.currentItem = current_step-1
        }
    }

    private fun backStep(progress: Int) {
        var progress = progress
        if (progress > 1) {
            progress--
            current_step = progress
            binding.viewPager.currentItem = current_step-1
        }
    }




    companion object {
        private const val MAX_STEP = 3
    }

    class ViewPagerAdapter(fm: FragmentActivity?) : FragmentStateAdapter(fm!!) {


        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return SiteFormFragment(0)
                1 -> return SiteFormFragment(1)
            }
            return SourceFormFragment(0)
        }
    }
}