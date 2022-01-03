package com.example.appnotsure

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appnotsure.databinding.ActivityMainBinding
import java.time.Duration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActivityLink()
        validationParticipants()

    }

    /**
     * Validate number participantes correct
     * Invalid number for example -1 or 0
     */
    private fun validationParticipants() {
        val button = binding.buttonStart
        val inputText = binding.editTextParticipants
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

    /**
     * Navigation settings the activity to fragment
     */
    private fun setupActivityLink() {
        val linkTextView = binding.activityIntentNavigationLink
        linkTextView.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment, TermsAndConditionsFragment())
                .commit()
            hideComponents()
        }
    }

    /**
     * Hide activity components in the fragment
     */
    private fun hideComponents() {
        binding.buttonStart.visibility = View.GONE
        binding.editTextParticipants.visibility = View.GONE
        binding.tvTitle.visibility = View.GONE
    }
}