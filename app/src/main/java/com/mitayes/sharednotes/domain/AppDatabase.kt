package com.mitayes.sharednotes.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mitayes.sharednotes.data.sqlite.RootNotesDBEntity

private const val NOTE_DATABASE_NAME = "SharedNotes"

@Database(
    version = 1,
    entities = [
        RootNotesDBEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rootNotesDao(): RootNotesDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance?: synchronized(this) { buildDatabase(context).also { instance = it} }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = NOTE_DATABASE_NAME
            ).build()
        }
    }
}
