package com.example.bookly

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val allBooks: LiveData<List<Book>>
    private val repository:BookRepository

    init {
        val dao = BookDatabase.getDatabase(application).getBookDao()
        repository = BookRepository(dao)
        allBooks = repository.allBooks
    }

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(book)
    }

    fun insertBook(book: Book) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(book)
    }

}