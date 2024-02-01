package com.tatara.libraryapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.tatara.libraryapp.room.BookEntity
import com.tatara.libraryapp.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(model: BookViewModel, bookId: String?, navController: NavHostController) {
    var newBookTitle by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = newBookTitle,
            onValueChange = { enteredText ->
                newBookTitle = enteredText
            },
            label = { Text(text = "Update book name") },
            placeholder = { Text(text = "New book name") }
        )
        Button(onClick = {
            val newBook = BookEntity(bookId!!.toInt(), newBookTitle)
            model.updateBook(newBook)
            navController.popBackStack()
        }) {
            Text(text = "Update book")
        }
    }
}