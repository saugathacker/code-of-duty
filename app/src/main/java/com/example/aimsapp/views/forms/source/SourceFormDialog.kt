package com.example.aimsapp.views.forms.source

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.databinding.DialogSourceFormBinding

/**
 * This pops up once the driver reaches the source
 */
class SourceFormDialog(wayPoint: WayPoint) : DialogFragment() {
    private var current_step = 1
    private lateinit var binding: DialogSourceFormBinding
    var wayPoint= wayPoint

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext(), R.style.DialogTheme)
        dialog.setContentView(binding.root)



//        return AlertDialog.Builder(activity, R.style.DialogTheme)
//            .setView(binding.root)
//            .create()
        return dialog
    }

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_source_form,
            null,
            false
        )
        initComponent()
        val pagerAdapter = ViewPagerAdapter(this, wayPoint)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false
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

    class ViewPagerAdapter(fm: SourceFormDialog, wayPoint: WayPoint) : FragmentStateAdapter(fm!!) {

        private var wayPoint = wayPoint

        override fun getItemCount(): Int {
            return 2
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return SourceFormFragment(0, wayPoint)
                1 -> return SourceFormFragment(1, wayPoint)

            }
            return SourceFormFragment(0, wayPoint)
        }
    }
}