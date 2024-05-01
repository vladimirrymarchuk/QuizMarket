package com.example.quizmarket.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizmarket.ui.theme.Pink200

@Composable
fun QuizMarketButton(title: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 15.dp, end = 15.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Pink200)
    ) {
        Text(
            text = title,
            color = Color.White,
            style = TextStyle(fontSize = 20.sp),
        )
    }
}