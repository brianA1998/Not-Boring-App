package com.example.appnotsure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.appnotsure.databinding.FragmentSuggestionBinding

class SuggestionFragment : Fragment() {
    private lateinit var binding: FragmentSuggestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Hide the random button icon
     */
    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.btn_random)
        item.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Enables the return button that removes the fragment from the stack
        (activity as ActivitiesActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding = FragmentSuggestionBinding.inflate(inflater, container, false)

        val activityName: TextView = binding.tvActivityName
        val participantsNumberTextView: TextView = binding.tvParticipants
        val activityPrice: TextView = binding.tvPrice
        val activityTypeTextView: TextView = binding.tvActivityType
        val tryAnother: Button = binding.btnTryAnother

        if (arguments != null) {
            val activityType: String = arguments?.getString(Constants.ACTIVITY_TYPE_SET_KEY) ?: ""

            tryAnother.setOnClickListener {
                if (activityType.isEmpty()) (activity as ActivitiesActivity).getAnotherSuggestion()
                else (activity as ActivitiesActivity).getAnotherSuggestionByType()
            }


            val activityTypeLL: LinearLayout = binding.llActivityType
            //If the "activity_type" argument is empty it means that the fragment
            // corresponds to a random activity so sets the activity view group to visible
            if (activityType.isEmpty()) {
                activityTypeLL.visibility = View.VISIBLE
                val typeValue: String = arguments?.getString(Constants.ACTIVITY_TYPE_KEY) ?: ""
                activityTypeTextView.text = capitalize(typeValue)
            } else {
                activityTypeLL.visibility = View.INVISIBLE
            }

            val error: String? = arguments?.getString(Constants.ACTIVITY_ERROR_KEY)

            if (error.isNullOrEmpty()) {

                val name = arguments?.getString(Constants.ACTIVITY_NAME_KEY)
                val participants = arguments?.getInt(Constants.PARTICIPANTS_NUMBER_KEY)
                val price = arguments?.getDouble(Constants.ACTIVITY_PRICE_KEY)

                activityName.text = name
                participantsNumberTextView.text = participants.toString()

                price?.let {
                    when {
                        price == 0.0 -> activityPrice.text = Constants.PRICE_FREE
                        price <= 0.3 -> activityPrice.text = Constants.PRICE_LOW
                        price <= 0.6 -> activityPrice.text = Constants.PRICE_MEDIUM
                        else -> activityPrice.text = Constants.PRICE_HIGH
                    }
                } ?: run { activityPrice.text = Constants.UNKNOWN }

            } else {
                activityName.text = error
                binding.llActivityPrice.visibility = View.INVISIBLE
                binding.llParticipantsNum.visibility = View.INVISIBLE
            }
        } else {
            Toast.makeText(context, Constants.ERROR_DEFAULT, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun capitalize(word: String): String {
        return "${word.substring(0, 1).uppercase()}${word.substring(1, word.length)}"
    }
}