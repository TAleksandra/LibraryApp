package com.tatara.libraryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatara.libraryapp.repository.Repository
import com.tatara.libraryapp.room.BookEntity
import com.tatara.libraryapp.room.BooksDB
import kotlinx.coroutines.launch

class BookViewModel(val repository: Repository) : ViewModel() {

    fun addBook(book: BookEntity) {
        viewModelScope.launch {
            repository.insertBookToRoom(book)
        }
    }

    val getAllBooks = repository.getAllBooks()

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            repository.deleteBookFromRoom(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            repository.updateBookInRoom(book)
        }
    }

}