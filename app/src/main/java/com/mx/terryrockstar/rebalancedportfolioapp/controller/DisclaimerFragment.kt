package com.mx.terryrockstar.rebalancedportfolioapp.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentDisclaimerBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DisclaimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisclaimerFragment : Fragment() {

    private var _binding: FragmentDisclaimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisclaimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.linkPrivacyPolice.setOnClickListener {
            Toast.makeText(context, "Privacy", Toast.LENGTH_SHORT).show()
        }
        binding.startButton.setOnClickListener {
            Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DisclaimerFragment.
         */
        @JvmStatic
        fun newInstance() = DisclaimerFragment().apply {}
    }
}