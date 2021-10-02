package com.dev.abhinav.reachmobisportsapp.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.abhinav.reachmobisportsapp.model.Event
import com.dev.abhinav.reachmobisportsapp.model.Team
import com.dev.abhinav.reachmobisportsapp.model.TeamResponse
import com.dev.abhinav.reachmobisportsapp.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel(private val repository: Repository) : ViewModel() {

    private val _eventsLiveData = MutableLiveData<List<Event>>()
    private val _teamDetailLiveData = MutableLiveData<List<Team>>()
    private var _teamId = MutableLiveData<String>()

    val eventsLiveData: LiveData<List<Event>>
        get() = _eventsLiveData
    val teamDetailsLiveData: LiveData<List<Team>>
        get() = _teamDetailLiveData
    val teamId: LiveData<String>
        get() = _teamId

    fun getLastTeamEvents(teamId: String) {
//        repository.getTeamLastFiveEvents(teamId).doOnSubscribe{}.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<EventResponse> {
//                override fun onSubscribe(d: Disposable) {
//                    disposable.add(d)
//                }
//
//                override fun onNext(t: EventResponse) {
//                    _eventsLiveData.postValue(t.results)
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.d("Error", e.message.toString())
//                }
//
//                override fun onComplete() {
//                }
//            })
    }

    fun getTeamId(teamName: String) {
        val call = repository.getTeamDetails(teamName)
        Log.d("aaa", call.request().url.toString())
        //var teamId = ""

        call.enqueue(object : Callback<TeamResponse> {
            override fun onResponse(call: Call<TeamResponse?>, response: Response<TeamResponse?>) {
                Log.d("aaa", response.body()!!.teams.toString())
//                for (i in 0..response.body()!!.teams.size) {
//                    if(response.body()!!.teams[i].teamName == binding.teamName.text) {
//                      val teamDetails1 = response.body()!!.teams[i].teamId
//                      val teamDetails2 = response.body()!!.teams[i].teamName
//                    }
//                }
                _teamId.postValue(response.body()!!.teams[0].teamId)
                //teamId = response.body()!!.teams[0].teamId
                //Log.d("aaa", teamId)
            }

            override fun onFailure(call: Call<TeamResponse?>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }
}