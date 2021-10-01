package com.dev.abhinav.reachmobisportsapp.repository

import com.dev.abhinav.reachmobisportsapp.api.Service

class EventRepository(private val api: Service) {

    fun getTeamLastFiveEvents(teamId: String) = api.getLastFiveEventsByTeamID(teamId)

    fun getTeamSeasonEvents(teamId: String, year: String) = api.getSeasonEventsByTeamID(teamId, year)
}