package com.phinion.animedetailsapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.phinion.animedetailsapp.data.local.BorutoDatabase
import com.phinion.animedetailsapp.data.repository.LocalDataSourceImpl
import com.phinion.animedetailsapp.domain.repository.LocalDataSource
import com.phinion.animedetailsapp.utils.Constants.BORUTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BorutoDatabase {
        return Room.databaseBuilder(
            context,
            BorutoDatabase::class.java,
            BORUTO_DATABASE
        ).build()
    }


    @Provides
    @Singleton
    fun provideLocalDataSource(
        database: BorutoDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            borutoDatabase = database
        )
    }

}