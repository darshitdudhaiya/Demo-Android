package com.example.demo

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {



        fun makeHttpGetRequest(urlString: String): String {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection

            try {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                return response.toString()
            } finally {
                connection.disconnect()
            }
        }



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val receivedIntent = intent
        val username = receivedIntent.getStringExtra("name")
        val gender = receivedIntent.getStringExtra("gender")

        val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
        val genderTextView = findViewById<TextView>(R.id.genderTextView)
        val profilePicture = findViewById<ImageView>(R.id.profileImage)

        usernameTextView.text = username
        genderTextView.text = gender
        if (gender == "Male") {
            profilePicture.setImageResource(R.drawable.male)
        } else if (gender == "Female") {
            profilePicture.setImageResource(R.drawable.female)
        }


        val editProfileButton = findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener {}
    }
}



