package io.keiji.sample.mastodonclient.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import io.keiji.sample.mastodonclient.BuildConfig
import io.keiji.sample.mastodonclient.R
import io.keiji.sample.mastodonclient.databinding.FragmentLoginBinding
import kotlin.reflect.jvm.internal.impl.resolve.scopes.InnerClassesScopeWrapper

class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        val TAG = LoginFragment::class.java.simpleName

        private const val REDIRECT_URI = "auth://${BuildConfig.APPLICATION_ID}"
    }

    private var binding: FragmentLoginBinding? = null

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            BuildConfig.INSTANCE_URL,
            lifecycleScope,
            requireContext()
        )
    }

    interface Callback{
        fun onAuthCompleted()
    }

    private var callback: Callback? = null

    fun requestAccessToken(code: String) {
        viewModel.requestAccessToken(
            BuildConfig.CLIENT_KEY,
            BuildConfig.CLIENT_SECRET,
            REDIRECT_URI,
            BuildConfig.CLIENT_SCOPES,
            BuildConfig.CLIENT_SCOPES,
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Callback) {
            callback = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bindingData: FragmentLoginBinding? = DataBindingUtil.bind(view)
        binding = bindingData ?: return

        viewModel.accessTokenSaved.observe(viewLifecycleOwner, Observer {
            callback?.onAuthCompleted()
        })

        val authUri = Uri.parse(BuildConfig.INSTANCE_URL)
            .buildUpon()
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id",BuildConfig.CLIENT_KEY)
            .appendQueryParameter("redirect", REDIRECT_URI)
            .appendQueryParameter("response_type","code")
            .appendQueryParameter("scope",BuildConfig.CLIENT_SCOPES)
            .build()

        val intent = Intent(Intent.ACTION_VIEW,authUri).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        startActivity(intent)

        bindingData.webview.settings.javaScriptEnabled = true
        bindingData.webview.loadUrl(authUri.toString())

    }

}