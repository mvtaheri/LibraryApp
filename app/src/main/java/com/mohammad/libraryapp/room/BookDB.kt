package com.mohammad.libraryapp.room

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BookDB : RoomDatabase() {
    abstract fun bookDAO(): BookDAO

    companion object {
        @Volatile
        private var INSTANCE: BookDB? = null

        fun getInstance(context: Context): BookDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDB::class.java,
                        "books_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}