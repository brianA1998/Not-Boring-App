package com.example.appnotsure.listView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.appnotsure.R

class ActivitiesItemAdapter(private val context: Context, private var ActivitiesList: List<String>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return ActivitiesList.size
    }

    override fun getItem(position: Int): Any {
        return ActivitiesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.activities_list_item, parent, false)

        val cardTitle = rowView.findViewById<TextView>(R.id.tv_activity_name)

        val title = getItem(position) as String

        cardTitle.text = title

        return rowView
    }
}