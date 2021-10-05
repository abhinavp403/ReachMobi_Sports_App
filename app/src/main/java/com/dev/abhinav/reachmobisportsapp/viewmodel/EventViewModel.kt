package com.dev.abhinav.reachmobisportsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.abhinav.reachmobisportsapp.model.Event
import com.dev.abhinav.reachmobisportsapp.model.EventResponse
import com.dev.abhinav.reachmobisportsapp.model.TeamResponse
import com.dev.abhinav.reachmobisportsapp.repository.Repository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel(private val repository: Repository) : ViewModel() {

    private val seasonsList = listOf("2021-2022", "2020-2021", "2019-2020", "2018-2019", "2017-2018")

    private val _eventsLiveData = MutableLiveData<List<Event>>()
    private var _teamId = MutableLiveData<String>()
    private var _leagueId = MutableLiveData<String>()
    private var _leagueIds = MutableLiveData<List<String?>>()

    val eventsLiveData: LiveData<List<Event>>
        get() = _eventsLiveData
    val teamId: LiveData<String>
        get() = _teamId
    val leagueId: LiveData<String>
        get() = _leagueId
    val leagueIds: LiveData<List<String?>>
        get() = _leagueIds

    fun getLastFiveTeamEvents(teamId: String) {
        viewModelScope.launch {
            val call = repository.getTeamLastFiveEvents(teamId)
            Log.d("Apicall", call.request().url.toString())

            call.enqueue(object : Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse?>, response: Response<EventResponse?>) {
                    _eventsLiveData.postValue(response.body()!!.results)
                }

                override fun onFailure(call: Call<EventResponse?>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                }
            })
        }
    }

    fun getTeamSeasonEvents(leagueId: String, teamId: String) {
        viewModelScope.launch {
            for (year in seasonsList) {
                val call = repository.getTeamSeasonEvents(leagueId, year)
                Log.d("Apicall", call.request().url.toString())

                call.enqueue(object : Callback<EventResponse> {
                    override fun onResponse(call: Call<EventResponse?>, response: Response<EventResponse?>) {
                        val events = response.body()!!.events
                        if(events != null) {
                            val eventsList = mutableListOf<Event>()
                            for (i in response.body()!!.events.indices) {
                                if (events[i].status != "Not Started" || events[i].status != "NS") {
                                    if (events[i].homeTeamId == teamId || events[i].awayTeamId == teamId) {
                                        viewModelScope.launch {
                                            events[i].homeBadge = getTeamBadge(events[i].homeTeam)
                                            events[i].awayBadge = getTeamBadge(events[i].awayTeam)
                                        }
                                        eventsList.add(events[i])
                                    }
                                }
                            }
                            _eventsLiveData.postValue(eventsList)
                        }
                    }

                    override fun onFailure(call: Call<EventResponse?>, t: Throwable) {
                        Log.d("Error", t.message.toString())
                    }
                })
            }
        }
    }

    fun getTeamAndLeagueId(teamName: String) {
        viewModelScope.launch {
            val call = repository.getTeamDetails(teamName)
            Log.d("Apicall", call.request().url.toString())

            call.enqueue(object : Callback<TeamResponse> {
                override fun onResponse(call: Call<TeamResponse?>, response: Response<TeamResponse?>) {
                    for (i in response.body()!!.teams.indices) {
                        if(response.body()!!.teams[i].teamName == teamName || response.body()!!.teams[i].teamNameAlternate == teamName) {
                           _teamId.postValue(response.body()!!.teams[i].teamId)
                            _leagueId.postValue(response.body()!!.teams[i].leagueId1)
                            val leagueIdList = mutableListOf<String>()
                            leagueIdList.add(response.body()!!.teams[i].leagueId1)
                            leagueIdList.add(response.body()!!.teams[i].leagueId2)
                            leagueIdList.add(response.body()!!.teams[i].leagueId3)
                            leagueIdList.add(response.body()!!.teams[i].leagueId4)
                            leagueIdList.add(response.body()!!.teams[i].leagueId5)
                            leagueIdList.add(response.body()!!.teams[i].leagueId6)
                            leagueIdList.add(response.body()!!.teams[i].leagueId7)
                            _leagueIds.postValue(leagueIdList)
                            break
                        }
                    }
                }

                override fun onFailure(call: Call<TeamResponse?>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                }
            })
        }
    }

    suspend fun getTeamBadge(teamName: String): String {
        val badge = CompletableDeferred<String>()
        viewModelScope.launch {
            val call = repository.getTeamDetails(teamName)
            call.enqueue(object : Callback<TeamResponse> {
                override fun onResponse(call: Call<TeamResponse?>, response: Response<TeamResponse?>) {
                    badge.complete(response.body()!!.teams[0].teamBadge)
                }

                override fun onFailure(call: Call<TeamResponse?>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                }
            })
        }
        return badge.await()
    }
}