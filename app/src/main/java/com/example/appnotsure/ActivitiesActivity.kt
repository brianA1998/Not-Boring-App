package com.example.appnotsure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appnotsure.databinding.ActivityActivitiesBinding
import com.example.appnotsure.listView.ActivitiesItemAdapter
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.appnotsure.retroFit.BoredResponse
import com.example.appnotsure.retroFit.NotBoredApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ActivitiesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesBinding
    private lateinit var toolbar: Toolbar
    private lateinit var listView: ListView
    private var type: String = ""
    private var numberParticipants: Int = Constants.DEFAULT_NUMBER_PARTICIPANTS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        //Removes the default toolbar title
        this.supportActionBar!!.setDisplayShowTitleEnabled(false)
        listViewInit()

        //Sets the number of participants as an argument to be passed inside the intent
        val bundle: Bundle? = intent.extras
        numberParticipants = bundle?.get(Constants.PARTICIPANTS_KEY) as Int

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activities, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * When the random button is tapped sets the type to an empty String
     * Sets the title in the toolbar to "Random"
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        type = ""
        when (item.itemId) {
            R.id.btn_random -> getSuggestion()
        }
        val title: TextView = toolbar.findViewById(R.id.toolbar_title)
        title.text = "Random"
        return super.onOptionsItemSelected(item)
    }

    /**
     * When the back button is tapped sets the title in the toolbar to "Activities"
     * Removes the back arrow button of the toolbar
     */
    override fun onSupportNavigateUp(): Boolean {
        val title: TextView = toolbar.findViewById(R.id.toolbar_title)
        title.text = "Activities"
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        onBackPressed()
        return true
    }

    private fun listViewInit() {
        listView = binding.lvActivities
        val arrayAdapter = ActivitiesItemAdapter(this, Constants.LIST_OF_TYPES_OF_ACTIVITIES)
        listView.adapter = arrayAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            type = Constants.LIST_OF_TYPES_OF_ACTIVITIES[position]
            getSuggestionByType()
        }

    }

    /**
     * Gets a random activity from the Bored API
     * Then if the call is successful do the transaction to put the suggestion fragment
     */
    private fun getSuggestion() {
        val progressBar: ProgressBar = binding.pbActivities
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {

            val suggestionResponse: BoredResponse?

            val call = getRetrofit()
                .create(NotBoredApiService::class.java)
                .getRandomActivity(numberParticipants)

            suggestionResponse = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    doTransaction(suggestionResponse)

                    progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(this@ActivitiesActivity, "Some error: ${call.errorBody().toString()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    /**
     * Gets an activity from the Bored API from a particular activity type
     * Then if the call is successful do the transaction to put the suggestion fragment
     * Uses the activity type to set the title in the toolbar
     */
    private fun getSuggestionByType() {
        val progressBar: ProgressBar = binding.pbActivities
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {

            val suggestionResponse: BoredResponse?

            val call = getRetrofit()
                .create(NotBoredApiService::class.java)
                .getActivityByType(type.lowercase(), numberParticipants)

            suggestionResponse = call.body()

            runOnUiThread {
                if (call.isSuccessful) {

                    val title: TextView = toolbar.findViewById(R.id.toolbar_title)
                    title.text = suggestionResponse?.type?.let { capitalize(it) }

                    doTransaction(suggestionResponse)

                    progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(this@ActivitiesActivity, "Some error: ${call.errorBody().toString()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    /**
     * Pops the fragment in the back stack
     * Gets a random activity from the Bored API
     * Then if the call is successful do the transaction to put the suggestion fragment
     */
    fun getAnotherSuggestion() {
        supportFragmentManager.popBackStack()
        getSuggestion()
    }

    /**
     * Pops the fragment in the back stack
     * Gets an activity from the Bored API from a particular activity type
     * Then if the call is successful do the transaction to put the suggestion fragment
     * Uses the activity type to set the title in the toolbar
     */
    fun getAnotherSuggestionByType() {
        supportFragmentManager.popBackStack()
        getSuggestionByType()
    }

    /**
     * Creates the Bundle object with the data of the API response
     * and pass that data to the fragment.
     *
     * @param suggestionResponse is the response obtained from the API
     *
     */
    private fun doTransaction(suggestionResponse: BoredResponse?) {
        val transaction = supportFragmentManager
        val suggestionFragment = SuggestionFragment()
        val data = Bundle()

        data.putInt("participants_number", numberParticipants)
        data.putString("activity_type", type)

        data.putString("name", suggestionResponse?.activity)
        suggestionResponse?.participants?.let { data.putInt("participants", it) }
        suggestionResponse?.price?.let { data.putDouble("price", it) }
        data.putString("type", suggestionResponse?.type)
        data.putString("error", suggestionResponse?.error)

        suggestionFragment.arguments = data

        transaction.beginTransaction()
            .replace(R.id.frag_container, suggestionFragment)
            .addToBackStack("suggestionFragment")
            .commit()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun capitalize(word: String): String {
        return "${word.substring(0, 1).uppercase()}${word.substring(1, word.length)}"
    }
}