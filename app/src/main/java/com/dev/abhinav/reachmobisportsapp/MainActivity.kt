package com.dev.abhinav.reachmobisportsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev.abhinav.reachmobisportsapp.adapter.EventListAdapter
import com.dev.abhinav.reachmobisportsapp.api.Service
import com.dev.abhinav.reachmobisportsapp.databinding.ActivityMainBinding
import com.dev.abhinav.reachmobisportsapp.model.Event
import com.dev.abhinav.reachmobisportsapp.repository.Repository
import com.dev.abhinav.reachmobisportsapp.viewmodel.EventViewModel
import com.dev.abhinav.reachmobisportsapp.viewmodel.EventViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var teamId: String = ""
    private var leagueId: String = ""
    private lateinit var viewModel: EventViewModel
    private lateinit var retrofitService: Service
    private lateinit var adapter: EventListAdapter
    private val eventsList = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        retrofitService = Service.getInstance()
        adapter = EventListAdapter()
        binding.eventRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, EventViewModelFactory(Repository(retrofitService))).get(
            EventViewModel::class.java
        )

        binding.goButton.setOnClickListener {
            val teamName = binding.teamName.text.toString()
            getTeamId(teamName)
        }
    }

    private fun getTeamId(teamName: String) {
        viewModel.getTeamAndLeagueId(teamName)
        viewModel.teamId.observe(this) {
            lifecycleScope.launch {
                teamId = it
                //getEventDetails()
            }
        }
        viewModel.leagueId.observe(this) {
            lifecycleScope.launch {
                leagueId = it
                //getEventDetails()
            }
        }
        viewModel.leagueIds.observe(this) { it ->
            lifecycleScope.launch {
                it.forEach {
                    if(it != null) {
                        leagueId = it
                        getEventDetails()
                    }
                }
            }
            viewModel.eventsLiveData.observe(this@MainActivity) { it ->
                eventsList.addAll(it)
                eventsList.sortByDescending {
                    it.eventDate
                }
                adapter.setEventList(eventsList)
            }
        }
    }

    private fun getEventDetails() {
        //viewModel.getLastFiveTeamEvents(teamId)
        viewModel.getTeamSeasonEvents(leagueId, teamId)
//        viewModel.eventsLiveData.observe(this) {
//            lifecycleScope.launch {
//                adapter.setEventList(it)
//            }
//        }
    }
}