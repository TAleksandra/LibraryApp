package com.tatara.libraryapp.repository

import com.tatara.libraryapp.room.BookEntity
import com.tatara.libraryapp.room.BooksDB
import kotlinx.coroutines.flow.Flow

class Repository(val booksDB: BooksDB) {

    suspend fun insertBookToRoom(bookEntity: BookEntity) {
        booksDB.bookDao().insertBook(bookEntity)
    }

    fun getAllBooks() = booksDB.bookDao().getAllBooks()

    suspend fun deleteBookFromRoom(bookEntity: BookEntity) {
        booksDB.bookDao().deleteBook(bookEntity)
    }

    suspend fun updateBookInRoom(bookEntity: BookEntity) {
        booksDB.bookDao().updateBook(bookEntity)
    }

}