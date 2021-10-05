package com.dev.abhinav.reachmobisportsapp.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    val teamId: String,
    @SerializedName("strTeam")
    val teamName: String,
    @SerializedName("strAlternate")
    val teamNameAlternate: String,
    @SerializedName("strTeamBadge")
    val teamBadge: String,
    @SerializedName("idLeague")
    val leagueId1: String,
    @SerializedName("strLeague")
    val leagueName1: String,
    @SerializedName("idLeague2")
    val leagueId2: String,
    @SerializedName("strLeague2")
    val leagueName2: String,
    @SerializedName("idLeague3")
    val leagueId3: String,
    @SerializedName("strLeague3")
    val leagueNam3: String,
    @SerializedName("idLeague4")
    val leagueId4: String,
    @SerializedName("strLeague4")
    val leagueName4: String,
    @SerializedName("idLeague5")
    val leagueId5: String,
    @SerializedName("strLeague5")
    val leagueName5: String,
    @SerializedName("idLeague6")
    val leagueId6: String,
    @SerializedName("strLeague6")
    val leagueName6: String,
    @SerializedName("idLeague7")
    val leagueId7: String,
    @SerializedName("strLeague7")
    val leagueName7: String
)
