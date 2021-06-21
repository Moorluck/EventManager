package be.bxl.eventsmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bxl.eventsmanager.R
import be.bxl.eventsmanager.adapters.EventOfDayAdapter
import be.bxl.eventsmanager.models.Event
import java.time.LocalDate

class EventsManagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_events_manager, container, false)

        val rvEvents : RecyclerView = v.findViewById(R.id.rv_event_of_day)

        val event1 = Event(1, "Aller chez Vincent", LocalDate.now())
        val event2 = Event(1, "Aller au concert", LocalDate.now())
        val event3 = Event(1, "Faire les courses pour noel", LocalDate.now())

        val events1 = mutableListOf(event1, event2, event3)

        val event4 = Event(1, "Manger", LocalDate.of(2021, 6, 22))
        val event5 = Event(1, "Boire", LocalDate.of(2021, 6, 22))
        val event6 = Event(1, "Dormir", LocalDate.of(2021, 6, 22))

        val events2 = mutableListOf(event4, event5, event6)

        val events = mutableListOf(events1, events2)

        var adapter = EventOfDayAdapter(context)
        adapter.eventsOfDay = events

        rvEvents.adapter = adapter
        rvEvents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() = EventsManagerFragment()
    }
}