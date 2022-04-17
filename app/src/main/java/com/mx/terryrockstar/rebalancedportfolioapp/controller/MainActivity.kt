package com.mx.terryrockstar.rebalancedportfolioapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mx.terryrockstar.rebalancedportfolioapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RebalancedPortfolioApp)
        setContentView(R.layout.activity_main)
    }
}