package com.swyp.moodit.data.di

import com.swyp.moodit.data.impl.AuthRepositoryImpl
import com.swyp.moodit.data.impl.MissionRepositoryImpl
import com.swyp.moodit.data.impl.ReportRepositoryImpl
import com.swyp.moodit.data.impl.TournamentRepositoryImpl
import com.swyp.moodit.data.impl.UserRepositoryImpl
import com.swyp.moodit.data.repository.AuthRepository
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.data.repository.ReportRepository
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    internal abstract fun bindTournamentRepository(tournamentRepositoryImpl: TournamentRepositoryImpl): TournamentRepository

    @Binds
    @Singleton
    internal abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    internal abstract fun bindMissionRepository(missionRepositoryImpl: MissionRepositoryImpl): MissionRepository

    @Binds
    @Singleton
    internal abstract fun bindReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository
}