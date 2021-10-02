package com.dev.abhinav.reachmobisportsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dev.abhinav.reachmobisportsapp.api.Service
import com.dev.abhinav.reachmobisportsapp.databinding.ActivityMainBinding
import com.dev.abhinav.reachmobisportsapp.repository.Repository
import com.dev.abhinav.reachmobisportsapp.viewmodel.EventViewModel
import com.dev.abhinav.reachmobisportsapp.viewmodel.EventViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var teamId: String = ""
    private lateinit var viewModel: EventViewModel
    private lateinit var retrofitService: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //val teamName = binding.teamName.text
        val teamName = "Arsenal"
        retrofitService = Service.getInstance()

        viewModel = ViewModelProvider(this, EventViewModelFactory(Repository(retrofitService))).get(
            EventViewModel::class.java
        )

        viewModel.getTeamId(teamName)
        viewModel.teamId.observe(this, Observer {
            Log.d("aaa2", it)
            teamId = it
        })


//        val service = Client().getClient().create(Service::class.java)
//        val call = service.getTeamID(teamName)
//        Log.d("aaa", call.request().url.toString())

//        viewModel.getTeamName("Arsenal")
//        val teamDetails = viewModel.teamDetailsLiveData.value
//        Log.d("aaa", teamDetails.toString())

//        call.enqueue(object : Callback<TeamResponse> {
//            override fun onResponse(call: Call<TeamResponse?>, response: Response<TeamResponse?>) {
////                for (i in 0..response.body()!!.teams.size) {
////                    if(response.body()!!.teams[i].teamName == binding.teamName.text) {
////                      val teamDetails1 = response.body()!!.teams[i].teamId
////                      val teamDetails2 = response.body()!!.teams[i].teamName
////                    }
////                }
//                teamId = response.body()!!.teams[0].teamId
//            }
//
//            override fun onFailure(call: Call<TeamResponse?>, t: Throwable) {
//                Log.d("Error", t.message.toString())
//            }
//        })
//    }
    }
}