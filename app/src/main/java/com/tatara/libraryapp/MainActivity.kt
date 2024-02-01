package com.tatara.libraryapp

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tatara.libraryapp.repository.Repository
import com.tatara.libraryapp.room.BookEntity
import com.tatara.libraryapp.room.BooksDB
import com.tatara.libraryapp.screens.UpdateScreen
import com.tatara.libraryapp.ui.theme.LibraryAppTheme
import com.tatara.libraryapp.viewmodel.BookViewModel

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
                    val myContext = LocalContext.current
                    val db = BooksDB.getInstance(myContext)
                    val myRepository = Repository(db)
                    val myModel = BookViewModel(myRepository)


                    // navigation
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "MainScreen"
                    ) {
                        composable("MainScreen") {
                            MainScreen(model = myModel, navController)
                        }
                        composable("UpdateScreen/{bookId}") {
                            UpdateScreen(
                                model = myModel,
                                bookId = it.arguments?.getString("bookId"),
                                navController
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
fun MainScreen(model: BookViewModel, navController: NavHostController) {
    var inputBook by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = inputBook,
            onValueChange = { enteredText ->
                inputBook = enteredText
            },
            label = { Text(text = "Book name") },
            placeholder = { Text(text = "Enter book name") }
        )
        Button(onClick = {
            model.addBook(BookEntity(0, inputBook))
        }) {
            Text(text = "Insert book into db")
        }
        BooksList(model = model, navController)
    }
}

@Composable
fun BookCard(model: BookViewModel, book: BookEntity, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${book.id}.",
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )
            Text(
                text = book.title,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { model.deleteBook(book) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
            IconButton(onClick = {
                navController.navigate("UpdateScreen/${book.id}")
            }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
            }
        }
    }
}

@Composable
fun BooksList(model: BookViewModel, navController: NavHostController) {
    val books by model.getAllBooks.collectAsState(initial = emptyList())

    LazyColumn() {
        items(items = books) { item ->
            BookCard(model = model, book = item, navController)
        }
    }
}