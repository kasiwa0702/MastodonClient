package io.keiji.sample.mastodonclient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.keiji.sample.mastodonclient.databinding.FragmentMainBinding
import retrofit2.Retrofit
import android.util.Log
import com.google.android.material.slider.BaseOnSliderTouchListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment:Fragment(R.layout.fragment_main) {

    class MainFragment : MainFragment(R.layout.fragment_main) {

    companion object{
        private val TAG = MainFragment::class.java.simpleName
        private const val API_BASE_URL = "https://androok2020.keiji.io"
    }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

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
                val tootList = api.fetchPublicTimeline()
                ShowTootList(tootList)
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
    }
    private suspend fun showTootList(
        tootList: List<Toot>
    ) = withContext(Dispatchers.Main) {
        val binding = binding ?: return@withContext
        val accountNameList = tootList.map { it.account.displayName}
        binding.button.text = accountNameList.joinToString ("\n")
    }
    }

}