package com.example.aimsapp.views.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aimsapp.R
import com.here.android.mpa.guidance.NavigationManager
import com.here.android.mpa.guidance.VoiceCatalog
import com.here.android.mpa.guidance.VoiceSkin

class VoiceSkinsActivity : AppCompatActivity() {
    private var m_voiceSkinsView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_skins)
        title = "Voice Skins"
        m_voiceSkinsView = findViewById(R.id.voicesList)
        val button = findViewById<Button>(R.id.downloadButton)
        button.setOnClickListener {
            val intent =
                Intent(this@VoiceSkinsActivity, VoicePackagesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshVoiceSkins()
    }

    private fun refreshVoiceSkins() {
        // Fill the list of locally downloaded voices.
        val catalog = VoiceCatalog.getInstance()
        val voiceSkins = catalog.localVoiceSkins
        val adapter =
            VoiceSkinsAdapter(this, voiceSkins)
        m_voiceSkinsView!!.adapter = adapter
    }

    private class VoiceSkinsAdapter internal constructor(
        context: Context?,
        private val m_voiceSkins: List<VoiceSkin>
    ) :
        RecyclerView.Adapter<VoiceSkinsAdapter.VoiceViewHolder>() {
        private val m_inflater: LayoutInflater
        private var m_selectedId: Long = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceViewHolder {
            val view: View =
                m_inflater.inflate(R.layout.voice_skin_item, parent, false)
            return VoiceViewHolder(view)
        }


        override fun getItemCount(): Int {
            return m_voiceSkins.size
        }

        private class VoiceViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            var m_selectedView: RadioButton
            var m_idView: TextView
            var m_languageView: TextView
            var m_typeView: TextView

            init {
                m_selectedView = itemView.findViewById(R.id.voiceSelected)
                m_idView = itemView.findViewById(R.id.voiceId)
                m_languageView = itemView.findViewById(R.id.voiceLanguage)
                m_typeView = itemView.findViewById(R.id.voiceType)
            }
        }

        init {
            m_inflater = LayoutInflater.from(context)

            // get the id of the currently selected voice skin
            val selectedVoiceSkin =
                NavigationManager.getInstance().voiceGuidanceOptions.voiceSkin
            if (selectedVoiceSkin != null) {
                m_selectedId = selectedVoiceSkin.id
            } else {
                for (voiceSkin in m_voiceSkins) {
                    if (voiceSkin.language == "None") {
                        m_selectedId = voiceSkin.id
                        break
                    }
                }
            }
        }

        override fun onBindViewHolder(holder: VoiceViewHolder, position: Int) {
            val voiceSkin = m_voiceSkins[position]
            holder.m_idView.text = java.lang.Long.toString(voiceSkin.id)
            holder.m_languageView.text = voiceSkin.language
            holder.m_typeView.text = voiceSkin.outputType.toString()
            holder.m_selectedView.isChecked = m_selectedId == voiceSkin.id
            holder.itemView.setOnClickListener { // set voice skin for navigation
                NavigationManager.getInstance().voiceGuidanceOptions
                    .setVoiceSkin(m_voiceSkins[position])
                m_selectedId = m_voiceSkins[position].id
                notifyItemRangeChanged(0, m_voiceSkins.size)
            }
        }

    }
}