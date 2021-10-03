package io.keiji.sample.mastodonclient.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import io.keiji.sample.mastodonclient.BuildConfig
import io.keiji.sample.mastodonclient.R
import io.keiji.sample.mastodonclient.databinding.FragmentLoginBinding
import kotlin.reflect.jvm.internal.impl.resolve.scopes.InnerClassesScopeWrapper

class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        val TAG = LoginFragment::class.java.simpleName
    }

    private var binding: FragmentLoginBinding? = null

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            BuildConfig.INSTANCE_URL,
            lifecycleScope,
            requireContext()
        )
    }

    private val onObtainCode = fun (code: String) {


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bindingData: FragmentLoginBinding? = DataBindingUtil.bind(view)
        binding = bindingData ?: return

        val authUri = Uri.parse(BuildConfig.INSTANCE_URL)
            .buildUpon()
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id",BuildConfig.CLIENT_KEY)
            .appendQueryParameter("redirect",BuildConfig.CLIENT_REDIRECT_URI)
            .appendQueryParameter("response_type","code")
            .appendQueryParameter("scope",BuildConfig.CLIENT_SCOPES)
            .build()

        bindingData.webview.webViewClient = InnerWebViewClient(onObtainCode)
        bindingData.webview.settings.javaScriptEnabled = true
        bindingData.webview.loadUrl(authUri.toString())

    }

    private class InnerWebViewClient(
        val onObtainCode: (code: String) -> Unit
    ) : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view ?: return

            val code = Uri.parse(view.url).getQueryParameter("code")
            code ?: return

            onObtainCode(code)
        }
    }
}