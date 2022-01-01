package com.example.bookly

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("Select * from books_table order by bookName ASC")
    fun getAllBooks(): LiveData<List<Book>>
}