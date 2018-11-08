package com.example.trjano.festivapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trjano.festivapp.database.EventItem

class EventListAdapter(private var mItems : ArrayList<EventItem>) :
        RecyclerView.Adapter<EventListAdapter.MyViewHolder>() {
    var onItemClick: ((String) -> Unit)? = null

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                onItemClick?.invoke(mItems[adapterPosition].toString())
            }
        }
    }

    //Returns item for given position
    fun getItem(pos: Int): EventItem {
        return mItems[pos]
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): EventListAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_list_item, parent, false) as View




        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view)
    }

    //Adds an event to the adapter list
    fun add(item: EventItem) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    //Clears the event list from the adapter
    fun clear() {

        mItems.clear()
        notifyDataSetChanged()

    }

    //Assigns a list of events to adapter
    fun load(items: List<EventItem>) {

        mItems.clear()
        mItems = ArrayList(items)
        notifyDataSetChanged()

    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        // - get element from your dataset at this position
        // - replace the contents of the view with that element

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mItems.size
}