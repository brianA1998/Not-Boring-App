package com.example.appnotsure

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
                button.isEnabled = false
                inputText.error = Constants.WRONG_NUMBER_OF_PARTICIPANTS_MESSAGE
                inputText.requestFocus()
            } else {
                launchActivityActivities(numberParticipants)
            }
        }

        binding.editTextParticipants.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val startButton: Button = binding.buttonStart
                val number: Int = Integer.parseInt(s.toString())
                startButton.isEnabled = number >= 1
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })
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
     * Navigation from home screen to screen activities
     *
     * @param numberParticipants is the number of app participants
     *
     */
    private fun launchActivityActivities(numberParticipants: Int) {
        val intentLaunchActivities: Intent = Intent(this, ActivitiesActivity::class.java)
        intentLaunchActivities.putExtra(Constants.PARTICIPANTS_KEY, numberParticipants)
        startActivity(intentLaunchActivities)
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