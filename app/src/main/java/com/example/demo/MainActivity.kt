package com.example.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits", "Recycle", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = openOrCreateDatabase("demoDB.db", MODE_PRIVATE, null)


        findViewById<Button>(R.id.btnLogin).setOnClickListener {

            val username = findViewById<EditText>(R.id.txtUsername).text.toString()
            val password = findViewById<EditText>(R.id.txtPassword).text.toString()

            val cursor : Cursor = database.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                arrayOf((username).trim(),password.trim())
            )


            if (cursor.count == 1) {
                cursor.moveToFirst()

                val my_prefs = getSharedPreferences("my_prefs", MODE_PRIVATE)
                val editor = my_prefs.edit()

                editor.putInt("user_id",cursor.getInt(cursor.getColumnIndex("id")))
                editor.putString("username",cursor.getString(cursor.getColumnIndex("username")))

                editor.apply()


                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                finish()
            } else {
                Toast.makeText(this, "Wrong Credentials !", Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {


            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

            finish()

        }

    }
}