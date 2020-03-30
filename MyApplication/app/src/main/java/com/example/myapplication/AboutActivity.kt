package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val listView = findViewById<ListView>(R.id.sensor_list)
        val sensorList = mutableListOf<String>()
        (getSystemService(SENSOR_SERVICE) as SensorManager).getSensorList(Sensor.TYPE_ALL).forEach {
            val text = it.name
            sensorList.add(text)
        }
        listView.adapter = ArrayAdapter<String>(this, R.layout.activity_listview, sensorList)
    }
}
