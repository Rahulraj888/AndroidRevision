package com.example.myapplication.viewmodel

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.room.AppDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
//    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(applicationContext)
        val userDao = db.userDao()

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(userDao) as T
            }
        })[LoginViewModel::class.java]


        val emailInput = binding.emailInput
        val passwordInput = binding.passwordInput

        binding.btnLogin.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.login(email, password)
        }

        viewModel.loginResult.observe(this) { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.users.collect { userList ->
                // Update UI
                Log.d("UserFragment", "Users: $userList")
            }
        }

        viewModel.onQueryChanged("test")
        lifecycleScope.launchWhenStarted {
            viewModel.searchResults.collect { results ->
                // Update UI with results
                Log.d("SearchFragment", "Results: $results")
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UIState.Loading -> {
                        // Show progress bar
                    }
                    is UIState.Success -> {
                        // Update RecyclerView or UI with state.data
                    }
                    is UIState.Error -> {
                        // Show Toast or Snackbar with state.message
                    }
                }
            }
        }
    }
}