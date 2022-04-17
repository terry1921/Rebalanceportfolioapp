package com.mx.terryrockstar.rebalancedportfolioapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RebalancedPortfolioApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.linkPrivacyPolice.setOnClickListener {  }
        binding.startButton.setOnClickListener {  }
    }

}