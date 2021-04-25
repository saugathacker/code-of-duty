package com.example.aimsapp.views.forms

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aimsapp.R
import com.example.aimsapp.databinding.DialogSourceFormBinding
import kotlinx.android.synthetic.main.dialog_source_form.*


class SourceFormDialog : DialogFragment() {
    private var current_step = 1
    private var status: TextView? = null
    private lateinit var binding: DialogSourceFormBinding


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
            R.layout.dialog_source_form,
            null,
            false
        )
        initComponent()
        val pagerAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = pagerAdapter

    }

    private fun initComponent() {
        binding.lytBack.setOnClickListener {
            backStep(current_step)
        }
        binding.lytNext.setOnClickListener {
            nextStep(current_step)
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
                0 -> return SourceFormFragment(0)
                1 -> return SourceFormFragment(1)

            }
            return SourceFormFragment(0)
        }
    }
}