package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstNameEditText = findViewById<EditText>(R.id.firstNameEditText)
        val PasswordEditText = findViewById<EditText>(R.id.PasswordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)

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
                Toast.makeText(this, "$firstName $password $confirmPassword $selectedGender Please fill in all required fields", Toast.LENGTH_LONG).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "$password $confirmPassword Passwords are not matched!", Toast.LENGTH_LONG).show()
            }  else{
                val intent = Intent(this,ProfileActivity::class.java)
                intent.putExtra("name",firstName)
                intent.putExtra("gender",selectedGender)
                startActivity(intent)
                finish()
            }
        }
    }
}