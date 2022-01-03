package com.example.appnotsure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appnotsure.databinding.ActivityActivitiesBinding
import com.example.appnotsure.listView.ActivitiesItemAdapter
import android.view.Menu
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

    private var activityTypesList = arrayListOf(
        "education",
        "recreational",
        "social",
        "diy",
        "charity",
        "cooking",
        "relaxation",
        "music",
        "busywork"
    )
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        this.supportActionBar!!.setDisplayShowTitleEnabled(false);

        listViewInit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activities, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun listViewInit() {
        listView = binding.lvActivities
        val arrayAdapter = ActivitiesItemAdapter(this, activityTypesList)
        listView.adapter = arrayAdapter
        listView.isClickable = true
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            getSuggestion()

        }

    }

    private fun getSuggestion() {

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
                } else {
                    Toast.makeText(this@ActivitiesActivity, "Shit happens", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://www.boredapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}