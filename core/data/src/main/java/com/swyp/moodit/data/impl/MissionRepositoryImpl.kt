package com.swyp.moodit.data.impl

import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.network.api.MooditApi
import javax.inject.Inject

internal class MissionRepositoryImpl @Inject constructor(
    private val mooditApi: MooditApi
): MissionRepository {
}