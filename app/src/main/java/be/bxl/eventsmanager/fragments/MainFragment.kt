package be.bxl.eventsmanager.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bxl.eventsmanager.EventRepository
import be.bxl.eventsmanager.R
import be.bxl.eventsmanager.adapters.EventAdapter
import be.bxl.eventsmanager.db.EventDAO
import be.bxl.eventsmanager.models.Event


class MainFragment : Fragment() {

    // Views

    private lateinit var tvDate : TextView
    private lateinit var tvTemperature : TextView
    private lateinit var tvCity : TextView

    private lateinit var rvToday : RecyclerView
    private lateinit var rvTomorrow : RecyclerView

    // Data

    private lateinit var repository : EventRepository

    private lateinit var todayEvents : MutableList<Event>
    private lateinit var tomorrowEvents : MutableList<Event>

    // Adapter

    private lateinit var todayAdapter : EventAdapter
    private lateinit var tomorrowAdapter : EventAdapter

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


        // Get Event from database
        repository = EventRepository.newInstance(requireActivity().applicationContext)

        repository.getListOfTodayEvent {
            todayEvents = it
        }

        repository.getListOfTomorrowEvent {
            tomorrowEvents = it
        }

        // Setup rv

        todayAdapter = EventAdapter {
            onDeleteBtnClickListener?.invoke(it)
        }
        todayAdapter.events = this.todayEvents

        rvToday.adapter = todayAdapter
        rvToday.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        tomorrowAdapter = EventAdapter {
            onDeleteBtnClickListener?.invoke(it)
        }
        tomorrowAdapter.events = this.tomorrowEvents

        rvTomorrow.adapter = tomorrowAdapter
        rvTomorrow.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        return v
    }

    fun updateList() {
        repository.getListOfTodayEvent {
            todayEvents = it
        }
        repository.getListOfTomorrowEvent {
            tomorrowEvents = it
        }

        todayAdapter.events = todayEvents
        tomorrowAdapter.events = tomorrowEvents
    }

    private var onDeleteBtnClickListener : ((Int) -> Unit)? = null

    fun setOnDeleteBtnClickListener (lambda: (Int) -> Unit) {
        onDeleteBtnClickListener = lambda
    }

    companion object {
        @JvmStatic
        public fun newInstance() = MainFragment()
    }
}