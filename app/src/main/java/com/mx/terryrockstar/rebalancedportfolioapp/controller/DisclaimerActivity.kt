package com.mx.terryrockstar.rebalancedportfolioapp.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.ActivityDisclaimerBinding

class DisclaimerActivity : AppCompatActivity() {

    private var _binding: ActivityDisclaimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RebalancedPortfolioApp)
        _binding = ActivityDisclaimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI() {
        binding.linkPrivacyPolice.setOnClickListener {
            val intent = Intent(this@DisclaimerActivity, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
        binding.startButton.setOnClickListener {
            Toast.makeText(this@DisclaimerActivity, "Start", Toast.LENGTH_SHORT).show()
        }
    }

}