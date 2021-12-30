package com.example.appnotsure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.appnotsure.databinding.ActivityActivitiesBinding
import androidx.appcompat.widget.Toolbar
import com.example.appnotsure.databinding.TbNotBoredBinding
import com.example.appnotsure.listView.ActivitiesItemAdapter


class ActivitiesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesBinding
    private lateinit var bindingToolbar: TbNotBoredBinding
    private var activityTypesList = listOf(
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
        bindingToolbar = TbNotBoredBinding.inflate(layoutInflater)
        val toolBar: Toolbar = bindingToolbar.root
        setSupportActionBar(toolBar)

        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolBar.title = "Activities"
        listViewInit()

    }



    private fun listViewInit() {
        listView = binding.lvActivities
        val arrayAdapter = ActivitiesItemAdapter(this, activityTypesList)
        listView.adapter = arrayAdapter
    }
}