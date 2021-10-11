package io.keiji.sample.mastodonclient.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import io.keiji.sample.mastodonclient.entity.UserCredential
import io.keiji.sample.mastodonclient.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserCredentialRepository (
    private val application: Application
    ) {

    companion object{
        private const val KEY_ACCESS_TOKEN = "access_token"
    }

    private fun getPreference(instanceUrl: String): SharedPreferences? {
        val hostnameVerifier =  Uri.parse(instanceUrl).host
            ?: return null
        val filename = "{&hostname}.det"
        return application.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE)
    }

    suspend fun set(
        userCredential: UserCredential
    ) = withContext(Dispatchers.IO) {
        val pref = getPreference(userCredential.instanceUrl)
        pref?.edit{
            putString(KEY_ACCESS_TOKEN,userCredential.instanceUrl)
        }

    }

    suspend fun find(
        instanceUrl: String,
        username: String
    ) : UserCredential? = withContext(Dispatchers.Main) {

        return@withContext UserCredential(BuildConfig.INSTANCE_URL,BuildConfig.USERNAME,BuildConfig.ACCESS_TOKEN)

//        val pref = getPreference(instanceUrl)
//        ?: return@withContext null
//
//        val accessToken = pref.getString(KEY_ACCESS_TOKEN,null)
//            ?: return@withContext null
//
//        return@withContext UserCredential(instanceUrl,username,accessToken)
    }
}