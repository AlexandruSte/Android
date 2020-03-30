package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        checkSwitch()

        val switch = findViewById<Switch>(R.id.switch_sync)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                val prefs = getSharedPreferences("sync", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("sync", isChecked)
                editor.commit()
            }
        }
    }

    private fun checkSwitch() {
        val checked = getSharedPreferences("sync", MODE_PRIVATE).getBoolean("sync", true)
        findViewById<Switch>(R.id.switch_sync).also {
            it.isChecked = checked
        }
    }
}
