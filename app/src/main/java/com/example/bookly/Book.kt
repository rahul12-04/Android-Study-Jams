package com.example.bookly

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_table")
class Book(@ColumnInfo(name="bookName")val bookName:String,
           @PrimaryKey @ColumnInfo(name="bookId")val id:Int,
           @ColumnInfo(name="bookName")val date:String) {

}