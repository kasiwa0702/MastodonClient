package io.keiji.sample.mastodonclient


import retrofit2.http.GET


interface MastodonApi  {

    @GET("api/vl/timelines/public")
    suspend fun fetchPublicTimeline(
    ): List<Toot>
}