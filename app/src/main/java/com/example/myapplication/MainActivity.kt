package com.example.myapplication

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mathutils.square
import com.example.myapplication.databinding.ActivityListBinding
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // call math util
        callMathUtil()

        binding.submitButton.setOnClickListener {
            val email = binding.email.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = "Invalid email address"
            } else {
                binding.emailLayout.error = null
                Toast.makeText(this, "Submitted: $email", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun callMathUtil() {
        val result = square(7)    // calls your library function
        println("7² = $result")
    }
}