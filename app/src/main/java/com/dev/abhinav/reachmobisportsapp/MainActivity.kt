package com.dev.abhinav.reachmobisportsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dev.abhinav.reachmobisportsapp.adapter.EventListAdapter
import com.dev.abhinav.reachmobisportsapp.api.Service
import com.dev.abhinav.reachmobisportsapp.databinding.ActivityMainBinding
import com.dev.abhinav.reachmobisportsapp.repository.Repository
import com.dev.abhinav.reachmobisportsapp.viewmodel.EventViewModel
import com.dev.abhinav.reachmobisportsapp.viewmodel.EventViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var teamId: String = "0"
    private lateinit var viewModel: EventViewModel
    private lateinit var retrofitService: Service
    private lateinit var adapter: EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //val teamName = binding.teamName.text
        val teamName = "Arsenal"
        retrofitService = Service.getInstance()
        adapter = EventListAdapter()

        viewModel = ViewModelProvider(this, EventViewModelFactory(Repository(retrofitService))).get(
            EventViewModel::class.java
        )
        Log.d("aaaMainActivity", "MainActivity")

        //ApiCall()
        getTeamId(teamName)
        //getEventDetails()
    }

//    private fun ApiCall() {
//        CoroutineScope(IO).launch {
//            val result = getTeamId("Arsenal")
//            Log.d("aaaMainActivityResult", result)
//            getEventDetails(result)
//        }
//    }

    private fun getTeamId(teamName: String) {
        viewModel.getTeamId(teamName)
        viewModel.teamId.observe(this@MainActivity) {
            teamId = it
            Log.d("aaaMainActivity", teamId)
        }
        Log.d("aaaMainActivity2", teamId)
        getEventDetails(teamId)
    }

    private fun getEventDetails(teamId: String) {
        viewModel.getLastFiveTeamEvents("133604")
        viewModel.eventsLiveData.observe(this) {
            Log.d("aaaMainActivity", "Inside Adapter")
            adapter.setEventList(it)
        }
    }
}