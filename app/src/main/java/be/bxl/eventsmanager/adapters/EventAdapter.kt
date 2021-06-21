package be.bxl.eventsmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.bxl.eventsmanager.R
import be.bxl.eventsmanager.models.Event

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // Data

    var events : MutableList<Event> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvEvent : TextView = itemView.findViewById(R.id.tv_event_item)
        val tvDate : TextView = itemView.findViewById(R.id.tv_date_item)
        val btnEdit : ImageView = itemView.findViewById(R.id.btn_edit_item)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val eventView = layoutInflater.inflate(R.layout.item_event, parent, false)

        return ViewHolder(eventView)
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {

        val event = events[position]

        holder.tvEvent.text = event.name
        holder.tvDate.text = event.limitDate.toString()

    }

    override fun getItemCount(): Int {
        return events.size
    }
}