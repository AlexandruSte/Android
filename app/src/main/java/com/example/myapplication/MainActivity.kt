package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private var products = listOf("Apples", "Bread", "Milk", "Eggs", "Flour", "Wipes")
    private val extra_message = "com.example.myapplication.MESSAGE"

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
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(extra_message, message)
            type = "text/plain"
        }
        startActivity(intent)
    }
}
