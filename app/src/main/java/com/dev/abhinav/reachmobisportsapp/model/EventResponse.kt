package com.dev.abhinav.reachmobisportsapp.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("results")
    val results: List<Event>
)