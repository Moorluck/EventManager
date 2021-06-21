package be.bxl.eventsmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import be.bxl.eventsmanager.fragments.EventsManagerFragment
import be.bxl.eventsmanager.fragments.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavView : BottomNavigationView
    lateinit var fragmentContainer : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupère les views
        bottomNavView = findViewById(R.id.bottom_bar_main)
        fragmentContainer = findViewById(R.id.fragment_container_main)

        // Récupère les fragments
        val mainFragment : Fragment = MainFragment.newInstance()
        val eventsManagerFragment : Fragment = EventsManagerFragment()

        // Récupère le fragment manager
        val fm = supportFragmentManager

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
}