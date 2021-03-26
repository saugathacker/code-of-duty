package com.example.code_of_duty

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.code_of_duty.locationDatabase.SavedLocation

fun formatLocations(locations: List<SavedLocation>): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("<h3>Here are your Saved Locations</h3>")
        locations.forEach {
            append("<br>")
            append("<b>${it.locationName}</b>:")
            append("\t${it.latitude}, ${it.longitude}<br>")
            if(it.isIndoor == 2131230919){
                append("This is indoor.")
            }
            else{
                append("This is outdoor.")
            }
            append("<br><br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}