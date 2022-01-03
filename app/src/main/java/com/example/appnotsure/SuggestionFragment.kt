package com.example.appnotsure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.appnotsure.databinding.FragmentSuggestionBinding


class SuggestionFragment : Fragment() {
    private lateinit var binding: FragmentSuggestionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuggestionBinding.inflate(inflater, container, false)

        val activityName: TextView = binding.tvActivityName
        val participantsNumber: TextView = binding.tvParticipants
        val activityPrice: TextView = binding.tvPrice
        val tryAnother: Button = binding.btnTryAnother

        if (arguments != null) {
            val name = arguments?.getString("name")
            val participants = arguments?.getInt("participants")
            val price = arguments?.getDouble("price")

            activityName.text = name
            participantsNumber.text = participants.toString()

            if (price != null) {
                when {
                    price == 0.0 -> activityPrice.text = "Free ${price}"
                    price <= 0.3 -> activityPrice.text = "Low ${price}"
                    price <= 0.6 -> activityPrice.text = "Medium ${price}"
                    else -> activityPrice.text = "High ${price}"
                }
            }
        } else {
            Toast.makeText(context, "Shit happens", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}