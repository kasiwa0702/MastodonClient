package io.keiji.sample.mastodonclient

import android.telecom.Call


interface MastodonApi  {

    @GET("api/vl/timelines/public")
    fun fetchPublicTimeline(

    ): Call<ResponseBody>
}