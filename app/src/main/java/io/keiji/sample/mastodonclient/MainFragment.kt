package io.keiji.sample.mastodonclient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.keiji.sample.mastodonclient.databinding.FragmentMainBinding

class MainFragment:Fragment(R.layout.fragment_main) {
    private  var binding: FragmentMainBinding? = null
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)
        binding?.button?.setOnClickListener {
        binding?.button?.text = "clicked"
    }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
    }
}