package io.keiji.sample.mastodonclient.repositor

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.keiji.sample.mastodonclient.MastodonApi
import io.keiji.sample.mastodonclient.entity.ResponseToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

public class AuthRepository (
        instanceUrl: String
) {

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    private val retrofit Retrofit.Builder()
          .bassUrl(instanceUrl)
          .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    private val api = retrofit.create(MastodonApi::class.java)

    suspend fun token(
            clientId: String,
            clientSecret: String,
            redirectUro: String,
            scopes: String,
            code: String
    ) : ResponseToken = withContext(Dispatchers.IO){
        return@withContext api.token(
                clientId,
                clientSecret,
                redirectUro,
                scopes,
                code,
                "authorization_code"
        )
    }
}