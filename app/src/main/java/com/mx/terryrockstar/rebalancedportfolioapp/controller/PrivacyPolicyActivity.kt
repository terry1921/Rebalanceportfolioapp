package com.mx.terryrockstar.rebalancedportfolioapp.controller

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.ActivityPrivacyPolicyBinding
import com.mx.terryrockstar.rebalancedportfolioapp.utils.ConnectionStatus
import com.mx.terryrockstar.rebalancedportfolioapp.utils.POLICY_LINK

class PrivacyPolicyActivity : AppCompatActivity() {

    private var _binding: ActivityPrivacyPolicyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val systemService = getSystemService(Context.CONNECTIVITY_SERVICE)
        checkConnection(systemService)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun checkConnection(systemService: Any?) {
        if (systemService != null) {
            val activeNetworkInfo = (systemService as ConnectivityManager).activeNetworkInfo
            val status =
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting) {
                    ConnectionStatus.NOT_CONNECTED
                } else if (activeNetworkInfo.type == 1) {
                    ConnectionStatus.CONNECTED_WIFI
                } else {
                    ConnectionStatus.CONNECTED
                }
            if (status == ConnectionStatus.NOT_CONNECTED) {
                Toast.makeText(this, R.string.please_check_connection, Toast.LENGTH_SHORT).show()
                finish()
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.webView.loadUrl(POLICY_LINK)
    }

}