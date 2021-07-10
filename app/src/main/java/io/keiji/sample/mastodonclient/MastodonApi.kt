package io.keiji.sample.mastodonclient


import retrofit2.http.GET
import okhttp3.ResponseBody


interface MastodonApi  {

    @GET("api/vl/timelines/public")
    suspend fun fetchPublicTimeline(
    ): ResponseBody
}