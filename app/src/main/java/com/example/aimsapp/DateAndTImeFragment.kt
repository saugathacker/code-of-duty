package com.example.aimsapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_source_form.*
import java.util.*

class DatePickerFragment(contentLayoutId: Int) : AppCompatActivity(contentLayoutId), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    var day=0
    var month=0
    var year=0
    var hour=0
    var minute=0
    var savedDay=0
    var savedMonth=0
    var savedHour=0
    var savedYear=0
    var savedMinute=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_delivered_form)

        pickDate()
    }
    private fun getDateTimeCalendar(){
        val cal:Calendar= Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)

    }
    private fun pickDate(){
        start_date.setOnClickListener{
    getDateTimeCalendar()
    DatePickerDialog(this,  this, year, month, day).show()
}
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth
        savedMonth=month
        savedYear=year
        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour=hourOfDay
        savedMinute=minute


    }
}