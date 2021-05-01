package com.example.aimsapp.views.forms

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.aimsapp.R
import com.example.aimsapp.databinding.SignaturePadBinding
import com.example.aimsapp.views.forms.site.SiteFormFragment
import com.example.aimsapp.views.forms.source.SourceFormFragment
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.*


class SignaturePad(num: Int) : DialogFragment() {
    private lateinit var binding: SignaturePadBinding
    private lateinit var mSignaturePad: SignaturePad
    private var mClearButton: Button? = null
    private var mSaveButton: Button? = null
    private lateinit var mDialog: Dialog
    private val no = num

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = Dialog(requireContext())
        mDialog.setContentView(binding.root)

        return mDialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.signature_pad,
            null,
            false
        )
        mSignaturePad = binding.signaturePad

        mSignaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                Toast.makeText(requireContext(), "OnStartSigning", Toast.LENGTH_SHORT).show()
            }

            override fun onSigned() {
                mSaveButton!!.isEnabled = true
                mClearButton!!.isEnabled = true
            }

            override fun onClear() {
                mSaveButton!!.isEnabled = false
                mClearButton!!.isEnabled = false
            }
        })


        mClearButton = binding.clearPad
        mSaveButton = binding.saveSignature
        mClearButton!!.setOnClickListener { mSignaturePad.clear() }
        mSaveButton!!.setOnClickListener {
            val signatureBitmap: Bitmap = mSignaturePad.getSignatureBitmap()
            if (no == 0){
                val parentFragment = requireParentFragment() as SourceFormFragment
                parentFragment.updateSignatureDisplay(signatureBitmap)
                mDialog.dismiss()
            }
            else{
                 val parentFragment = requireParentFragment() as SiteFormFragment
                parentFragment.updateSignatureDisplay(signatureBitmap)
                mDialog.dismiss()
            }
        }
    }


}