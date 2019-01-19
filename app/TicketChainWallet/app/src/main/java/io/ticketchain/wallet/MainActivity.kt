package io.ticketchain.wallet

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.ticketchain.wallet.iota.IOTAFactory
import io.ticketchain.wallet.iota.Transaction
import io.ticketchain.wallet.iota.Wallet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.StringBuilder
import kotlin.random.Random

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val iotaFactory: IOTAFactory = IOTAFactory()

    val wallet1Id = "ASDGTNBEF9KJDBTZLOHSBCJSTJJMABVC9HJBGZIDAHFVMNFQAXGAHJKF9VCGHCFGJCGHFNKLGFEGHJKS9"

    val wallet1: Wallet
    val wallet2: Wallet

    init {
        wallet1 = iotaFactory.createWallet()
        wallet1.id = wallet1Id
        wallet2 = iotaFactory.createWallet()
        wallet2.id = createWalletID()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        wallet1IdLabel.text = wallet1.id
        wallet2IdLabel.text = wallet2.id

        sendTransactionButton.setOnClickListener({
            val t = iotaFactory.createTransaction(wallet1, wallet2)
            t.sendMessage("Testmessage!")
        })

        createWallet1Address.setOnClickListener({
            wallet1.getValidAdress()
        })

        createWallet2Address.setOnClickListener({
            wallet2.getValidAdress()
        })

        listAllTransactionsButton.setOnClickListener({
            wallet1.getAllTokens()
            Log.d("Main", "tokensFromWallet2")
            wallet2.getAllTokens()
        })
    }

    private fun createWalletID(): String {
        val validChars = listOf(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'V', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '9'
        )
        val builder = StringBuilder()
        val rnd = Random(System.currentTimeMillis());
        for (i in 0..80) {
            val rndNum = Math.abs(rnd.nextInt() % validChars.size)
            builder.append(validChars[rndNum])
        }
        return builder.toString()
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
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
