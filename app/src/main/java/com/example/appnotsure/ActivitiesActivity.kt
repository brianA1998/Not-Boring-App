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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        this.supportActionBar!!.setDisplayShowTitleEnabled(false);

        listViewInit()

        val bundle: Bundle? = intent.extras
        val numberParticipants: Int = (bundle?.get(Constants.PARTICIPANTS_KEY) ?: 0) as Int
        println("La cantidad de participantes son : $numberParticipants")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activities, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.btn_random -> getSuggestion()
        }
        val title: TextView = toolbar.findViewById(R.id.toolbar_title)
        title.text = "Random"
        return super.onOptionsItemSelected(item)
    }

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
        listView.isClickable = true
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            getSuggestion()
        }

    }

    private fun getSuggestion() {
        val progressBar: ProgressBar = binding.pbActivities
        progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {

            val suggestionResponse: BoredResponse?

            val call = getRetrofit()
                .create(NotBoredApiService::class.java)
                .getRandomActivity()

            suggestionResponse = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val transaction = supportFragmentManager
                    val suggestionFragment = SuggestionFragment()
                    val data = Bundle()

                    data.putString("name", suggestionResponse?.activity)
                    data.putInt("participants", suggestionResponse?.participants!!)
                    data.putDouble("price", suggestionResponse?.price)

                    suggestionFragment.arguments = data

                    transaction.beginTransaction()
                        .replace(R.id.frag_container, suggestionFragment)
                        .addToBackStack("suggestionFragment")
                        .commit()

                    progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(this@ActivitiesActivity, "Shit happens", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}