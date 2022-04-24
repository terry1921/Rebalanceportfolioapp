package com.mx.terryrockstar.rebalancedportfolioapp.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.mx.terryrockstar.rebalancedportfolioapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}