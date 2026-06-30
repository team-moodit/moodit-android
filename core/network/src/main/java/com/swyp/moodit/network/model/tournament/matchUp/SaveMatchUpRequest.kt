package com.swyp.moodit.network.model.tournament.matchUp

data class SaveMatchUpRequest(
    val photoId: Long,
    val reasonId: Long,
    val matchUpId: Long
)
