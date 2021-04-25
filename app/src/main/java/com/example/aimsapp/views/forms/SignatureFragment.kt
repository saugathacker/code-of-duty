package com.example.aimsapp.views.forms



import android.os.Bundle
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*
import android.content.Context
import android.graphics.Bitmap
import android.content.ContextWrapper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import android.view.View
import androidx.core.content.ContextCompat
import com.example.aimsapp.R
import android.content.pm.ActivityInfo
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.aimsapp.databinding.SignaturePadBinding
import com.github.gcacace.signaturepad.views.SignaturePad
import kotlinx.android.synthetic.main.fragment_source_form.*


class SignatureFragment : AppCompatActivity() {
    private lateinit var binding: SignaturePadBinding
    var signaturePad: SignaturePad? = null
    var saveButton: Button? = null
    var clearButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signature_pad)
        binding = SignaturePadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signaturePad = findViewById<View>(R.id.signaturePad) as SignaturePad
        saveButton = findViewById<View>(R.id.saveButton) as Button
        clearButton = findViewById<View>(R.id.clearButton) as Button

        //disable both buttons at start
        binding.saveButton!!.isEnabled = false
        binding.clearButton!!.isEnabled = false

        //change screen orientation to landscape mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding.signaturePad!!.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {}
            override fun onSigned() {
                binding.saveButton!!.isEnabled = true
                binding.clearButton!!.isEnabled = true
            }

            override fun onClear() {
                binding.saveButton!!.isEnabled = false
                binding.clearButton!!.isEnabled = false
            }
        })
        binding.saveButton!!.setOnClickListener { //write code for saving the signature here
            val uri:Uri = saveImageToInternalStorage(R.drawable.ic_signature)

            // Display the internal storage saved image in image view
            signature_View.setImageURI(uri)

        }
        binding.clearButton!!.setOnClickListener {
            binding.signaturePad!!.clear() }
    }

    private fun saveImageToInternalStorage(drawableId:Int):Uri{
        // Get the image from drawable resource as drawable object
        val drawable = ContextCompat.getDrawable(applicationContext,drawableId)

        // Get the bitmap from drawable object
        val bitmap = (drawable as BitmapDrawable).bitmap

        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)


        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }
}