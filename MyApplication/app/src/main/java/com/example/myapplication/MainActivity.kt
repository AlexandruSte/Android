package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationProvider
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private var products = listOf("Apples", "Bread", "Milk", "Eggs", "Flour", "Wipes")
    private val extra_message = "com.example.myapplication.MESSAGE"
    private val save_filename = "appSettings.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter: ArrayAdapter<*> = ArrayAdapter<String>(
            this,
            R.layout.activity_listview, products
        )

        val listView: ListView = findViewById(R.id.mobile_list)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, view, _, _ ->
            run {
                val textView = findViewById<TextView>(R.id.item_description)
                textView.text = (view as TextView).text
            }
        }

        val pressView: ListView = findViewById(R.id.mobile_list)
        registerForContextMenu(pressView)
    }

    override fun onStart() {
        loadContent()
        super.onStart()
        print("Start")
    }

    override fun onResume() {
        super.onResume()
        print("Resume")
    }

    override fun onRestart() {
        super.onRestart()
        print("Restart")
    }

    override fun onPause() {
        super.onPause()
        print("Pause")
    }

    override fun onStop() {
        saveContent()
        super.onStop()
        print("Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        print("Destroy")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(
            "item_description",
            findViewById<TextView>(R.id.item_description).text.toString()
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            val textView = findViewById<TextView>(R.id.item_description)
            textView.text = savedInstanceState.getString("item_description")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu, popup.menu)
        popup.show()
    }

    fun showDialog(item: MenuItem) {
        val dialog = DataUsageDialogFragment()
        dialog.show(supportFragmentManager, "dialog")
    }

    fun exit(item: MenuItem) {
        exitProcess(0)
    }

    fun removeOption(item: MenuItem) {
        val textView = findViewById<TextView>(R.id.item_description)
        textView.text = "Shopping list"
    }

    fun removeFromList(item: MenuItem) {
        val itemId = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        products = products.minus(products[itemId])
        print(products)

        val adapter: ArrayAdapter<*> = ArrayAdapter<String>(
            this,
            R.layout.activity_listview, products
        )

        val listView: ListView = findViewById(R.id.mobile_list)
        listView.adapter = adapter
    }

    fun sendMessage(view: View?) {
        val editText = findViewById<TextView>(R.id.editText)
        val message = editText?.text?.toString().orEmpty()
        val intent = Intent(ACTION_SEND).apply {
            putExtra(extra_message, message)
            type = "text/plain"
        }
        startActivity(intent)
    }

    // Lab 5
    // Open PreferenceActivity
    fun openSettings(item: MenuItem) {
        startActivity(Intent(this, Settings::class.java))
    }

    // Save app information
    // saving last date app was opened
    private fun saveContent() {
        try {
            OutputStreamWriter(openFileOutput(save_filename, MODE_PRIVATE)).also {
                it.write(SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()))
                it.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadContent() {
        try {
            val inputStream = openFileInput(save_filename)
            if (inputStream != null) {
                val buffReader = BufferedReader(InputStreamReader(inputStream))
                print(buffReader.readLine())
            }
        } catch (e: IOException) {
            print(e.printStackTrace())
        }
    }

    // Lab 6
    // Create a new Activity in your application and display information from the sensors available on the device.(3 p)
    fun openAbout(item: MenuItem) {
        startActivity(Intent(this, AboutActivity::class.java))
    }


    fun openLocation(item: MenuItem){
        startActivity(Intent(this, LocationActivity::class.java))
    }
}
