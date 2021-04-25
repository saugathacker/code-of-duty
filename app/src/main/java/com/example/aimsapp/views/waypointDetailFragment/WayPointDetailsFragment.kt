package com.example.aimsapp.views.waypointDetailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.databinding.FragmentWaypointDetailsBinding
import com.example.aimsapp.views.forms.SourceFormDialog

class WayPointDetailsFragment : Fragment()
{
    private lateinit var binding: FragmentWaypointDetailsBinding
    private lateinit var viewModel: WayPointDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_waypoint_details, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).dao
        val wayPoint = WayPointDetailsFragmentArgs.fromBundle(
            requireArguments()
        ).selectedWayPoint
        val viewModelFactory =
            WayPointDetailsViewModelFactory(
                wayPoint,
                dataSource,
                application
            )

        viewModel = ViewModelProvider(this, viewModelFactory).get(WayPointDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.formsButton.setOnClickListener {
//            when(viewModel.selectedWayPoint.value!!.waypointTypeDescription){
//                "Source" -> this.findNavController().navigate(WayPointDetailsFragmentDirections.actionWayPointDetailsFragmentToSourceFormFragment())
//                else -> this.findNavController().navigate(WayPointDetailsFragmentDirections.actionWayPointDetailsFragmentToSiteFormFragment())
//            }
            val dialog = SourceFormDialog()
            dialog.show(childFragmentManager,"Source Form")
        }

        binding.navigateButton.setOnClickListener {
//            val alertDialogBuilder =
//                AlertDialog.Builder(requireActivity())
//            alertDialogBuilder.setTitle("Navigation feature is still under development")
//            alertDialogBuilder.setPositiveButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->
//                dialogInterface.dismiss()
//            }
//            val alertDialog = alertDialogBuilder.create()
//            alertDialog.show()
            viewModel.selectedWayPoint.value?.let { it1 ->
                WayPointDetailsFragmentDirections.actionWayPointDetailsFragmentToMap().latitude =
                    it1.latitude.toFloat()
                WayPointDetailsFragmentDirections.actionWayPointDetailsFragmentToMap().longitude =
                    it1.longitude.toFloat()
                this.findNavController().navigate(WayPointDetailsFragmentDirections.actionWayPointDetailsFragmentToMap().setLatitude(it1.latitude.toFloat()).setLongitude(it1.longitude.toFloat()))
            }


        }

        return binding.root
    }

}