package com.dev.abhinav.reachmobisportsapp.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("idEvent")
    val eventId: String = "",
    @SerializedName("strEvent")
    val eventName: String = "",
    @SerializedName("idLeague")
    val leagueId: String = "",
    @SerializedName("strLeague")
    val leagueName: String = "",
    @SerializedName("strSeason")
    val season: String = "",
    @SerializedName("strHomeTeam")
    val homeTeam: String = "",
    @SerializedName("strAwayTeam")
    val awayTeam: String = "",
    @SerializedName("intHomeScore")
    val homeScore: String = "",
    @SerializedName("intAwayScore")
    val awayScore: String = "",
    @SerializedName("idHomeTeam")
    val homeTeamId: String = "",
    @SerializedName("idAwayTeam")
    val awayTeamId: String = "",
    @SerializedName("dateEvent")
    val eventDate: String = "",
    @SerializedName("strVenue")
    val venue: String = "",
    @SerializedName("strStatus")
    val status: String = "",
    var homeBadge: String = "",
    var awayBadge: String = ""
)