package com.dev.abhinav.reachmobisportsapp.api

import com.dev.abhinav.reachmobisportsapp.model.Event
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("eventslast.php")
    fun getLastFiveEventsByTeamID(@Query("id") teamId: String): Call<Event>

    @GET("eventsseason.php")
    fun getSeasonEventsByTeamID(@Query("id") teamId: String, @Query("s") year: String): Call<Event>
}