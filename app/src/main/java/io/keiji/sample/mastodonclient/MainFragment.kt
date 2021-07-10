package io.keiji.sample.mastodonclient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.keiji.sample.mastodonclient.databinding.FragmentMainBinding
import retrofit2.Retrofit
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment:Fragment(R.layout.fragment_main) {

    companion object{
        private val TAG = MainFragment::class.java.simpleName
        private const val API_BASE_URL = "https://androok2020.keiji.io"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .build()
    private val api = retrofit.create(MastodonApi::class.java)

    private var binding: FragmentMainBinding? = null
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)
        binding?.button?.setOnClickListener {
            binding?.button?.text = "clicked"
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.fetchPublicTimeline().string()
                Log.d(TAG, response)
                withContext(Dispatchers.Main) {
                    binding?.button?.text = response
            }
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()

    }
}