package com.example.aimsapp.views.map


import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aimsapp.R
import com.here.android.mpa.guidance.VoiceCatalog
import com.here.android.mpa.guidance.VoicePackage
import kotlinx.coroutines.NonCancellable.cancel


class VoicePackagesActivity : AppCompatActivity() {
    private var m_progressBar: ProgressBar? = null
    private var m_packagesView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_packages)
        setTitle("Voice Packages")
        m_progressBar = findViewById(R.id.progressBar)
        m_packagesView = findViewById(R.id.voicePackagesView)
        downloadVoiceCatalog()
    }

    private fun downloadVoiceCatalog() {
        val voiceCatalog = VoiceCatalog.getInstance()

        // Download the catalog of voices if we haven't done so already.
        if (voiceCatalog.catalogList.isEmpty()) {
            voiceCatalog.downloadCatalog { error ->
                m_progressBar!!.visibility = View.GONE
                if (error == VoiceCatalog.Error.NONE) {
                    refreshVoicePackageList()
                } else {
                    Toast.makeText(
                        this@VoicePackagesActivity,
                        "Download catalog failed: $error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            m_progressBar!!.isIndeterminate = true
            m_progressBar!!.visibility = View.VISIBLE
        }
        refreshVoicePackageList()
    }

    private fun refreshVoicePackageList() {
        m_packagesView!!.adapter = VoicePackagesAdapter(this)
    }

    private class VoicePackagesAdapter(private val m_context: Context) :
        RecyclerView.Adapter<VoicePackagesAdapter.VoiceViewHolder>() {
        private val m_inflater: LayoutInflater = LayoutInflater.from(m_context)
        private var m_packages: List<VoicePackage>
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): VoiceViewHolder {
            val view: View =
                m_inflater.inflate(R.layout.voice_package_item, parent, false)
            return VoiceViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: VoiceViewHolder,
            position: Int
        ) {
            val voicePackage = m_packages[position]
            holder.m_isDownloaded.isChecked = voicePackage.isLocal
            holder.m_idView.text = java.lang.Long.toString(voicePackage.id)
            holder.m_nameView.text = voicePackage.name
            holder.m_marcView.text = voicePackage.marcCode
            holder.m_languageView.text = voicePackage.localizedLanguage
            holder.m_typeView.text = voicePackage.localizedType
            holder.m_sizeView.text = String.format(
                "%.2f Mb",
                voicePackage.downloadSize
            )
            holder.m_ttsView.text = "TTS : " + voicePackage.isTts
            holder.itemView.setOnClickListener(View.OnClickListener {
                val voiceCatalog = VoiceCatalog.getInstance()
                if (voiceCatalog.isDownloading) {
                    return@OnClickListener
                }

                // Check if the package has been already downloaded
                if (voicePackage.isLocal) {
                    AlertDialog.Builder(m_context)
                        .setMessage("Remove Package").setPositiveButton(
                            "OK",
                            DialogInterface.OnClickListener { dialog, which -> // Uninstall the package
                                voiceCatalog.deleteVoiceSkin(voicePackage.id)
                                m_packages = VoiceCatalog.getInstance().catalogList
                                notifyItemChanged(position)
                            }).setNegativeButton("Cancel", null).create().show()
                } else {
                    AlertDialog.Builder(m_context)
                        .setMessage("Install Package").setPositiveButton(
                            "OK",
                            DialogInterface.OnClickListener { dialog, which ->
                                holder.m_progressBar.visibility = View.VISIBLE

                                // Download and install the package
                                voiceCatalog.downloadVoice(
                                    voicePackage.id
                                ) {
                                    holder.m_progressBar.visibility = View.GONE
                                    notifyItemChanged(position)
                                }
                                voiceCatalog.setOnProgressEventListener { /*
                                                                                                                                                                         * The download progress can be retrieved
                                                                                                                                                                         * in this callback.
                                                                                                                                                                         */
                                }
                            }).setNegativeButton("Cancel", null).create().show()
                }
            })
        }

        override fun getItemCount(): Int {
            return m_packages.size
        }

        private class VoiceViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            var m_isDownloaded: CheckBox
            var m_idView: TextView
            var m_nameView: TextView
            var m_marcView: TextView
            var m_languageView: TextView
            var m_typeView: TextView
            var m_sizeView: TextView
            var m_ttsView: TextView
            var m_progressBar: ProgressBar

            init {
                m_isDownloaded = itemView.findViewById(R.id.voiceDownloaded)
                m_idView = itemView.findViewById(R.id.voiceId)
                m_nameView = itemView.findViewById(R.id.voiceName)
                m_marcView = itemView.findViewById(R.id.voiceMarc)
                m_languageView = itemView.findViewById(R.id.voiceLanguage)
                m_typeView = itemView.findViewById(R.id.voiceType)
                m_sizeView = itemView.findViewById(R.id.voiceSize)
                m_ttsView = itemView.findViewById(R.id.voiceTts)
                m_progressBar = itemView.findViewById(R.id.progressBar)
            }
        }

        init {
            // get a list of packages available for download
            m_packages = VoiceCatalog.getInstance().catalogList
        }
    }
}
