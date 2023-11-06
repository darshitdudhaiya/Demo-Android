package com.example.demo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Adapters.PersonRCView
//import android.widget.ArrayAdapter
//import android.widget.ListView
import com.example.demo.Helpers.Api
import com.example.demo.Models.Persons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    @SuppressLint("Recycle", "CutPasteId", "MissingInflatedId", "CommitPrefEdits", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val database = openOrCreateDatabase("demoDB.db", MODE_PRIVATE, null)

        val my_prefs = getSharedPreferences("my_prefs", MODE_PRIVATE)

        val id = my_prefs.getInt("user_id", 0)


        if (id == 0) {

            database.execSQL(
                /* sql = */ """
                    CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT,
                password TEXT,
                gender TEXT
            )
                """
            )

            database.close()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
            return
        }

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Persons>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(Persons("Person ${i}", "Numeber" + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = PersonRCView(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


//        findViewById<Button>(R.id.btnCountMinus).setOnClickListener {
//            var count = findViewById<TextView>(R.id.textCount).text.toString().toInt()
//
//            count -= 1
//
//            findViewById<TextView>(R.id.textCount).text = count.toString()
//        }
//
//        findViewById<Button>(R.id.btnCountPlus).setOnClickListener {
//            var count = findViewById<TextView>(R.id.textCount).text.toString().toInt()
//
//            count += 1
//
//            findViewById<TextView>(R.id.textCount).text = count.toString()
//        }

        findViewById<Button>(R.id.btnTest).setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)

            finish()
        }

        findViewById<Button>(R.id.btnApiCall).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = Api.request("api.php")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@HomeActivity, response, Toast.LENGTH_LONG).show()
                        Log.e("Exception Message",  "No message provided")
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@HomeActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()

                    }

                }
            }
        }

        findViewById<Button>(R.id.logout).setOnClickListener {

            val editor = my_prefs.edit()
            editor.remove("user_id")
            editor.apply()
//            Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}