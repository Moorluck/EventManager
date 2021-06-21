package be.bxl.eventsmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bxl.eventsmanager.R
import be.bxl.eventsmanager.adapters.EventAdapter
import be.bxl.eventsmanager.models.Event
import java.time.LocalDate


class MainFragment : Fragment() {

    // Views

    lateinit var tvDate : TextView
    lateinit var tvTemperature : TextView
    lateinit var tvCity : TextView

    lateinit var rvToday : RecyclerView
    lateinit var rvTomorrow : RecyclerView

    // Data

    lateinit var events : MutableList<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_main, container, false)

        tvDate = v.findViewById(R.id.tv_date_main)
        tvTemperature = v.findViewById(R.id.tv_temperature_main)
        tvCity = v.findViewById(R.id.tv_city_main)

        rvToday = v.findViewById(R.id.rv_today_main)
        rvTomorrow = v.findViewById(R.id.rv_tomorrow_main)

        val event1 = Event(1, "Aller chez Vincent", LocalDate.now())
        val event2 = Event(1, "Aller au concert", LocalDate.now())
        val event3 = Event(1, "Faire les courses pour noel", LocalDate.now())

        var todayAdapter = EventAdapter()
        todayAdapter.events = mutableListOf(event1, event2, event3)

        rvToday.adapter = todayAdapter

        rvToday.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}