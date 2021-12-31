package com.example.bookly

import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(book: Book)

    @Delete
    fun delete(book: Book)

    @Query("Select * from books_table order by bookName ASC")
    fun getAllBooks():List<Book>
}