package com.mohammad.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.mohammad.libraryapp.room.BookViewModel
import com.mohammad.libraryapp.ui.theme.LibraryAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohammad.libraryapp.room.BookDB
import com.mohammad.libraryapp.room.BookEntity
import com.mohammad.libraryapp.room.BookRepository
import com.mohammad.libraryapp.screen.UpdateScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val contex = LocalContext.current
                    val db = BookDB.getInstance(contex)
                    val repository = BookRepository(db)
                    val viewModel = BookViewModel(repository)
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen") {
                        composable("MainScreen") {
                            HomeScreen(bookViewModel = viewModel, navController)
                        }
                        composable("UpdateScreen/{bookId}") {
                            UpdateScreen(
                                viewModel = viewModel,
                                bookId = it.arguments?.getString("bookId")
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(bookViewModel: BookViewModel, navController: NavHostController) {
    var enterBook by remember {
        mutableStateOf("")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = enterBook,
            onValueChange = { enterdText ->
                enterBook = enterdText
            },
            label = { Text(text = "Enter The Book Name") },
            placeholder = { Text(text = "Enter Your Book Name") }
        )
        Button(
            onClick = {
                bookViewModel.addBook(BookEntity(0, enterBook))
            }
        ) {
            Text(text = "Insert Book Into DB")
        }
        BookList(viewModel = bookViewModel, navController)
    }
}

@Composable
fun BookCard(viewModel: BookViewModel, book: BookEntity, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row {
            Text(
                text = "" + book.id,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )
            Text(text = book.title, fontSize = 24.sp)
            IconButton(onClick = { viewModel.deleteBook(book = book) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
            IconButton(onClick = {
                navController.navigate("UpdateScreen/${book.id}")
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    }
}

@Composable
fun BookList(viewModel: BookViewModel, navController: NavHostController) {
    val books by viewModel.books.collectAsState(initial = emptyList())
    Column(Modifier.padding(16.dp)) {
        Text(text = "My Library", color = Color.Red, fontSize = 24.sp)
    }
    LazyColumn() {
        items(items = books) { item ->
            BookCard(
                viewModel = viewModel,
                book = item,
                navController
            )
        }
    }
}