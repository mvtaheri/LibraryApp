package com.mohammad.libraryapp.room

class BookRepository(val bookDB: BookDB) {

    suspend fun addBookToDB(bookEntity: BookEntity) {
        bookDB.bookDAO().addBook(bookEntity)
    }

    fun getAllBooks() = bookDB.bookDAO().getAllBooks()

    suspend fun deleteBookFromBook(book: BookEntity) {
        bookDB.bookDAO().delete(book)
    }

    suspend fun updateBook(book: BookEntity) {
        bookDB.bookDAO().update(book)
    }
}