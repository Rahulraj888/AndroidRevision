package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class JetPackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello, $name!", style = MaterialTheme.typography.titleLarge)
    }

    @Preview
    @Composable
    fun PreviewGreeting() {
        Greeting("Rahul")
    }
}