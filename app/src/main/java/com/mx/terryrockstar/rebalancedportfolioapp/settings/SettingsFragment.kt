package com.mx.terryrockstar.rebalancedportfolioapp.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Preferences
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Print

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val listener = Preference.OnPreferenceChangeListener { preference, newValue ->
            Print.i("preference: $preference newValue: $newValue")
            Preferences().setPreference(CURRENCY_PREFERENCE, newValue.toString())
            true
        }
        val listPreference: ListPreference? = findPreference("currency") as ListPreference?
        listPreference?.onPreferenceChangeListener = listener

    }
}

const val CURRENCY_PREFERENCE = "currencyPreference"