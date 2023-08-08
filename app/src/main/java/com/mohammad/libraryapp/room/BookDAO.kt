package com.mohammad.libraryapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDAO {

    @Insert
    suspend fun addBook(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Delete
    suspend fun delete(BookEntity: BookEntity)

    @Update
    suspend fun update(BookEntity: BookEntity)
}