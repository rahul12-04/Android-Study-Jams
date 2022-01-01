package com.example.bookly

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val allBooks: LiveData<List<Book>>

    init {
        val dao = BookDatabase.getDatabase(application).getBookDao()
        val repository = BookRepository(dao)
        allBooks = repository.allBooks
    }

}