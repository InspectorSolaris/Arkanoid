package com.example.lab3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Score::class], version = 1)
abstract class ScoresDatabase :
    RoomDatabase() {

    abstract fun scoreDao(): ScoreDao

}