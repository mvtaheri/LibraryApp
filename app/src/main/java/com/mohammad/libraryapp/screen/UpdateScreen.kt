package com.mohammad.libraryapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mohammad.libraryapp.room.BookViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.mohammad.libraryapp.room.BookEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(viewModel: BookViewModel, bookId: String?) {
    var inputBook by remember {
        mutableStateOf("")
    }
    Column {
        OutlinedTextField(
            value = inputBook,
            onValueChange = { newBook ->
                inputBook = newBook
            },
            label = { Text(text = "update book name") },
            placeholder = { Text(text = "new book name") }
        )
        Button(onClick = {
            viewModel.updateBook(BookEntity(bookId!!.toInt(), inputBook))
        }) {
            Text(text = "Update")
        }
    }

}