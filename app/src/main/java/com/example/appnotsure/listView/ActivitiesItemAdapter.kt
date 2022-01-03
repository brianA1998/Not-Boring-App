package com.example.appnotsure.listView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.appnotsure.R

class ActivitiesItemAdapter(private val context: Activity, private val activitiesList: ArrayList<String>) : ArrayAdapter<String>(context,R.layout.activity_activities, activitiesList) {

    override fun getItem(position: Int): String {
        return activitiesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)

        val rowView = inflater.inflate(R.layout.activities_list_item, null)

        val cardTitle = rowView.findViewById<TextView>(R.id.tv_activity_name)

        cardTitle.text = activitiesList[position]

        return rowView
    }
}