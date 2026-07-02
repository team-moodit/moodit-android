package com.swyp.moodit.data.impl

import com.swyp.moodit.data.repository.ReportRepository
import com.swyp.moodit.network.api.MooditApi
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val mooditApi: MooditApi
) : ReportRepository {
}