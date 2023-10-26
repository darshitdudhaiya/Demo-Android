package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btnLogin).setOnClickListener {

            val username = findViewById<EditText>(R.id.txtUsername).text.toString()
            val password = findViewById<EditText>(R.id.txtPassword).text.toString()

            if (username == "admin" && password == "admin") {
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