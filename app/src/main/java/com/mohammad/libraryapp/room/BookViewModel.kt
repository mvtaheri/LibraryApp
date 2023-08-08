package com.mohammad.libraryapp.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BookViewModel(val bookRepository: BookRepository) : ViewModel() {

    fun addBook(book: BookEntity) {
        viewModelScope.launch {
            bookRepository.addBookToDB(book)
        }
    }

    val books = bookRepository.getAllBooks()

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            bookRepository.deleteBookFromBook(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            bookRepository.updateBook(book)
        }
    }
}