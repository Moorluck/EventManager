package be.bxl.eventsmanager

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import be.bxl.eventsmanager.fragments.EventsManagerFragment
import be.bxl.eventsmanager.fragments.MainFragment
import be.bxl.eventsmanager.fragments.NewEventFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var fragmentContainer : FrameLayout

    // data

    private lateinit var repository : EventRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupère les views
        bottomNavView = findViewById(R.id.bottom_bar_main)
        fragmentContainer = findViewById(R.id.fragment_container_main)

        // Récupère le repository
        repository = EventRepository.newInstance(this)

        // Récupère le fragment manager
        val fm = supportFragmentManager

        // Récupère les fragments
        val mainFragment : MainFragment = MainFragment.newInstance()
        val newEventFragment = NewEventFragment.newInstance()
        val eventsManagerFragment : EventsManagerFragment = EventsManagerFragment.newInstance()

        // Set Delete btn
        mainFragment.setOnDeleteBtnClickListener {
            showDeleteDialog(it, mainFragment)
        }

        eventsManagerFragment.setOnDeleteBtnClickListener {
            showDeleteDialog(it, eventsManagerFragment)
        }

        // Set the button add
        eventsManagerFragment.setOnAddEventClickListener {
            val transaction = fm.beginTransaction().apply {
                replace(fragmentContainer.id, newEventFragment)
                addToBackStack(null)
            }
            transaction.commit()
        }

        // Affiche le fragment initial
        val initialTransaction = fm.beginTransaction().apply {
            replace(fragmentContainer.id, mainFragment)
        }
        initialTransaction.commit()


        bottomNavView.setOnNavigationItemSelectedListener {

            if (it.itemId == R.id.menu_today) {

                val transaction = fm.beginTransaction().apply {
                    replace(fragmentContainer.id, mainFragment)
                }
                transaction.commit()

            }
            else {

                val transaction = fm.beginTransaction().apply {
                    replace(fragmentContainer.id, eventsManagerFragment)
                }
                transaction.commit()
            }

            return@setOnNavigationItemSelectedListener true
        }


    }

    private fun showDeleteDialog(id : Int, fragment : Fragment) {

        repository.updateEvents()

        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle("Do you really want to delete this event ?")
            setPositiveButton("Yes") { _, _ ->
                repository.deleteEventById(id)
                updateFragment(fragment)
            }
            setNegativeButton("No") { _, _ ->

            }
        }
        alertDialog.show()
    }

    private fun updateFragment(fragment: Fragment) {
        if (fragment is MainFragment) {
            fragment.updateList()
        }

        if (fragment is EventsManagerFragment) {
            fragment.updateList()
        }
    }
}