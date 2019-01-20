package io.ticketchain.wallet

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.ticketchain.wallet.fragments.SendTicketFragment
import io.ticketchain.wallet.fragments.ShowAddressFragment
import io.ticketchain.wallet.fragments.TicketListFragment
import io.ticketchain.wallet.fragments.WelcomeFragment
import io.ticketchain.wallet.ether.EtherFactory
import io.ticketchain.wallet.ether.Wallet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val etherFactory: EtherFactory = EtherFactory(this)

    var wallet1: Wallet

    init {
        wallet1 = etherFactory.createWallet()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setActiveFragment(WelcomeFragment())

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_send_ticket -> {
                setActiveFragment(SendTicketFragment())
            }
            R.id.nav_show_adress -> {
                setActiveFragment(ShowAddressFragment())
            }
            R.id.nav_listtickets -> {
                setActiveFragment(TicketListFragment())
            }
            R.id.nav_manage_wallet -> {
                setActiveFragment(WelcomeFragment())
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setActiveFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit()
    }
}
