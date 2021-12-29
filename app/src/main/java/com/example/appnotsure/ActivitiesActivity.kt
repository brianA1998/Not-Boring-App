package com.example.appnotsure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.appnotsure.databinding.ActivityActivitiesBinding

class ActivitiesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesBinding
    private var activityTypesList = arrayOf(
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
        setSupportActionBar(findViewById(R.id.tb_not_bored))
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listViewInit()

    }

    private fun listViewInit() {
        listView = binding.lvActivities
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, activityTypesList)
        listView.adapter = arrayAdapter
    }
}