package com.dev.abhinav.reachmobisportsapp.api

import com.dev.abhinav.reachmobisportsapp.model.EventResponse
import com.dev.abhinav.reachmobisportsapp.model.TeamResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("eventslast.php")
    fun getLastFiveEventsByTeamID(@Query("id") teamId: String): Call<EventResponse>

    @GET("eventsseason.php")
    fun getSeasonEventsByTeamID(@Query("id") teamId: String, @Query("s") year: String): Call<EventResponse>

    @GET("searchteams.php")
    fun getTeamDetails(@Query("t") teamName: String): Call<TeamResponse>

    companion object {
        var service: Service? = null
        val BASE_URL : String = "https://www.thesportsdb.com/api/v1/json/1/"

        fun getInstance() : Service {
            if(service == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                service = retrofit.create(Service::class.java)
            }
            return service!!
        }
    }
}