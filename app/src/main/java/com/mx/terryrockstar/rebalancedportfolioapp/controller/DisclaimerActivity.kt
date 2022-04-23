package com.mx.terryrockstar.rebalancedportfolioapp.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.ActivityDisclaimerBinding
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Preferences

const val DISCLAIMER = "disclaimer"

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

        val isShowDisclaimer = Preferences().getPreference(DISCLAIMER, Boolean::class.java) as Boolean?

        if (isShowDisclaimer != null && isShowDisclaimer) {
            val intent = Intent(this@DisclaimerActivity, MainActivity::class.java)
            startActivity(intent)
            //finish()
        }
        binding.linkPrivacyPolice.setOnClickListener {
            val intent = Intent(this@DisclaimerActivity, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
        binding.startButton.setOnClickListener {
            Preferences().setPreference(DISCLAIMER, true)
            val intent = Intent(this@DisclaimerActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}