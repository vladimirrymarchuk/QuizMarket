package com.example.quizmarket.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun QuizMarketTextField(
    variable: MutableState<String>,
    label: String = "",
    singleLine: Boolean = true
){
    TextField(
        value = variable.value,
        shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray),
        onValueChange = { newText -> variable.value = newText },
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Color.DarkGray,
            disabledLabelColor = Color.Gray,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White
        ),
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
    )
}