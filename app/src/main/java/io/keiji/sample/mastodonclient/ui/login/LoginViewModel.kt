package io.keiji.sample.mastodonclient.ui.login

import android.app.Application
import android.util.Log
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.keiji.sample.mastodonclient.repository.AuthRepository
import io.keiji.sample.mastodonclient.repository.TootRepository
import io.keiji.sample.mastodonclient.repository.UserCredentialRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LoginViewModel (
 private val instanceUrl: String,
 private val coroutineScope: CoroutineScope,
 application: Application
) : AndroidViewModel(application) {

 companion object {
  private val TAG = AndroidViewModel::class.java.simpleName
 }

 private val authRepository = AuthRepository(instanceUrl)

 fun requestAccessToken(
  clientId: String,
  clientId: String,
  redirectUri: String,
  scopes: String,
  code: String
 ) {
  coroutineScope.launch {
   val responseToken = authRepository.token(
    instanceUrl,
    clientId,
    clientSecret,
    redirectUri,
    scopes,
    code
   )

   Log.d(TAG,responseToken.accessToken)
  }
 }
}