package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnCountMinus).setOnClickListener {
            var count = findViewById<TextView>(R.id.textCount).text.toString().toInt()

            count = count-1

            findViewById<TextView>(R.id.textCount).text = count.toString()
        }

        findViewById<Button>(R.id.btnCountPlus).setOnClickListener {
            var count = findViewById<TextView>(R.id.textCount).text.toString().toInt()

            count = count+1

            findViewById<TextView>(R.id.textCount).text = count.toString()
        }

//        findViewById<Button>(R.id.btnTest).setOnClickListener {
//            val intent = Intent(this,TestActivity::class.java)
//            startActivity(intent)
//
//            finish()
//        }
    }
}