package com.example.trjano.festivapp

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.finder_menu_list_item.view.*
import org.w3c.dom.Text

class FinderMenuListAdapter(private val myDataset: Array<String>) :
RecyclerView.Adapter<FinderMenuListAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FinderMenuListAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.finder_menu_list_item, parent, false) as View


        view.setOnClickListener{
            val intent = Intent(parent.context, EventListActivity::class.java)
            parent.context.startActivity(intent)
        }


        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.finder_menu_list_item_tv.text = myDataset[position]
        Log.d("Times", myDataset[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}