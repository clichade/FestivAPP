package com.example.trjano.festivapp.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trjano.festivapp.R
import com.example.trjano.festivapp.data.database.EventItem
import kotlinx.android.synthetic.main.event_list_item.view.*

class EventListAdapter(private var mItems : ArrayList<EventItem>) :
        RecyclerView.Adapter<EventListAdapter.MyViewHolder>() {
    var onItemClick: ((EventItem) -> Unit)? = null

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                onItemClick?.invoke(mItems[adapterPosition])
            }
        }
    }

    //Returns item for given position
    fun getItem(pos: Int): EventItem {
        return mItems[pos]
    }

    fun update(newItems: ArrayList<EventItem>){
        mItems.addAll(newItems)
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
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
        val event = getItem(position)
        holder.view.eventlist_item_label_name.text = event.name
        holder.view.eventlist_item_label_city.text = event.city + ":\n  " + event.location
        holder.view.evenlist_item_label_date.text = event.startDate

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mItems.size
}