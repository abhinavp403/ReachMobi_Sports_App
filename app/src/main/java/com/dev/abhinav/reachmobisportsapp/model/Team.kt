package com.dev.abhinav.reachmobisportsapp.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    val teamId: String,
    @SerializedName("strTeam")
    val teamName: String
)
