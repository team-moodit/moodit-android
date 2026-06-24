package com.swyp.moodit.network.model.tournament

import com.google.gson.annotations.SerializedName

data class CreateMoodMatchRequest(
    val title: String,
    @SerializedName("images")
    val photoIds: List<Long>
)