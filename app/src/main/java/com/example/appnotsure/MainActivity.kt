package com.example.appnotsure

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.time.Duration

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActivityLink()
        validationParticipants()

    }

    /**
     * Validate number participantes correct
     * Invalid number for example -1 or 0
     */
    private fun validationParticipants() {
        val button = findViewById<Button>(R.id.button_start)
        val inputText = findViewById<EditText>(R.id.edit_text_participants)
        inputText.error = null
        button.setOnClickListener {
            val numberParticipants: Int = Integer.parseInt(inputText.text.toString())
            if (numberParticipants < 1) {
                inputText.error = "Enter the correct number of participants"
                inputText.requestFocus()
            } else {
                //Navecion a la pantalla de activities
                Toast.makeText(this, "Todo correcto", Toast.LENGTH_SHORT).show()
            }


        }
    }


    private fun setupActivityLink() {
        val linkTextView = findViewById<TextView>(R.id.activity_intent_navigation_link)
        linkTextView.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TermsAndConditionsFragment()).commit()
        }
    }
}