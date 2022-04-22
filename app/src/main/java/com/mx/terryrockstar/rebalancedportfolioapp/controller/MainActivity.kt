package com.mx.terryrockstar.rebalancedportfolioapp.controller

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Print

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RebalancedPortfolioApp)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.materialToolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment? ?: return
        val navController = host.navController

        //appBarConfiguration = AppBarConfiguration(navController.graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Print.e(javaClass.name, e.message, e.cause)
                Integer.toString(destination.id)
            }

            Toast.makeText(this@MainActivity, "Navigated to $dest", Toast.LENGTH_SHORT).show()
            Print.d("MainActivity", "Navigated to $dest")
        }

        /*if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<DisclaimerFragment>(R.id.fragment_container)
            }
        }*/

    }

}