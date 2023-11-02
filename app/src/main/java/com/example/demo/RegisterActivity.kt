package com.example.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.SQLException

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstNameEditText = findViewById<EditText>(R.id.firstNameEditText)
        val PasswordEditText = findViewById<EditText>(R.id.PasswordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)

        val database = openOrCreateDatabase("demoDB.db", MODE_PRIVATE, null)



        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val password = PasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            val selectedRadioButtonId = genderRadioGroup.checkedRadioButtonId
            var selectedGender = ""

            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                selectedGender = selectedRadioButton.text.toString()
            }

            if (firstName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedGender == "") {
                Toast.makeText(
                    this,
                    "Please fill in all required fields",
                    Toast.LENGTH_LONG
                ).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords are not matched!", Toast.LENGTH_LONG).show()
            } else {
                val cursor: Cursor = database.rawQuery("SELECT * FROM users WHERE username = ?", arrayOf(firstName))

                if (cursor.count == 1) {
                    Toast.makeText(this, "Username Was Already Be Taken", Toast.LENGTH_LONG).show()
                }


                try {
                    val sql = "INSERT INTO users (username, password, gender) VALUES (?, ?, ?)"
                    val bindArgs = arrayOf(firstName, password, selectedGender)
                    database.execSQL(sql, bindArgs)
                } catch (e: SQLException) {
                    Toast.makeText(this, "${e}", Toast.LENGTH_SHORT).show()
                }

                cursor.close()

                database.close()

                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("name",firstName)
//                intent.putExtra("gender",selectedGender)
                startActivity(intent)
                finish()
            }
        }
    }
}