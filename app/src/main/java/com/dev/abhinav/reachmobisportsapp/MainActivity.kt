package com.dev.abhinav.reachmobisportsapp

import android.os.Bundle
import android.widget.Toast
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

        // Get Retrofit instance which is created in Service class
        retrofitService = Service.getInstance()

        // Create view model via view model factory
        viewModel = ViewModelProvider(this, EventViewModelFactory(Repository(retrofitService))).get(EventViewModel::class.java)

        // Set recycler view adapter
        adapter = EventListAdapter()
        binding.eventRecyclerView.adapter = adapter

        // Search api invoked when GO button is clicked
        binding.goButton.setOnClickListener {
            eventsList.clear()
            val teamName = binding.teamName.text.toString()
            getTeamLeagueId(teamName)
        }

        viewModel.status.observe(this) { status ->
            if(status == false) {
                Toast.makeText(applicationContext, "Team Not Found", Toast.LENGTH_LONG).show()
                adapter.setEventList(eventsList)
            }
        }

        viewModel.teamId.observe(this) {
            lifecycleScope.launch {
                teamId = it!!
                //getLastFiveEventDetails()
            }
        }

        viewModel.leagueIds.observe(this) { it ->
            lifecycleScope.launch {
                it!!.forEach {
                    if(it != null) {
                        leagueId = it
                        getSeasonEventDetails()
                    }
                }
            }
        }

        viewModel.eventsLiveData.observe(this@MainActivity) { events ->
            eventsList.addAll(events!!)
            eventsList.sortByDescending {
                it.eventDate
            }
            adapter.setEventList(eventsList)
        }
    }

    // Method to get team id and league id
    private fun getTeamLeagueId(teamName: String) {
        viewModel.getTeamAndLeagueId(teamName)
    }

    // Method to get events of last five seasons
    private fun getSeasonEventDetails() {
        viewModel.getTeamSeasonEvents(leagueId, teamId)
    }

    // Method to get last five events
    private fun getLastFiveEventDetails() {
        viewModel.getLastFiveTeamEvents(teamId)
        viewModel.eventsLiveData.observe(this) {
            lifecycleScope.launch {
                adapter.setEventList(it!!)
            }
        }
    }
}