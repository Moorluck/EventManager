package be.bxl.eventsmanager.models

import java.time.LocalDate

data class Event(
    val id : Int,
    var name : String,
    var limitDate : LocalDate
)
