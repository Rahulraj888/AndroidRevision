package com.example.myapplication.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.room.UserDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(private val userDao: UserDao) : ViewModel() {
    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    private val _users = MutableStateFlow<List<String>>(emptyList())
    val users: StateFlow<List<String>> = _users

    private val _uiState = MutableStateFlow<UIState<List<String>>>(UIState.Success(emptyList()))
    val uiState: StateFlow<UIState<List<String>>> = _uiState

    private val _query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            delay(1000) // Simulate API call
            _users.value = listOf("Alice", "Bob", "Charlie")
        }

        viewModelScope.launch {
            _query
                .debounce(300)
                .filter { it.length >= 3 }
                .flatMapLatest { query ->
                    flow {
                        try {
                            _uiState.value = UIState.Loading
                            val results = searchApi(query)
                            emit(UIState.Success(results))
                        } catch(exception: Exception) {
                            emit(UIState.Error("Failed to load results"))
                        }
                    }
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }

    val searchResults: StateFlow<List<String>> = _query
        .debounce(300) // Wait for user to pause typing
        .filter { it.length >= 3 } // Only search if input has 3+ characters
        .flatMapLatest { query ->  // Cancel previous API call if new one comes
            flow {
                try {
                    emit(searchApi(query)) // Simulated suspend API
                } catch (e: Exception) {
                    emit(emptyList()) // Emit empty list on error
                }
            }
        }.stateIn(  // Convert cold flow to hot StateFlow
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    suspend fun searchApi(query: String): List<String> {
        return listOf("rahul", "suresh")
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginResult.value = "Fields cannot be empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginResult.value = "Invalid email format"
        } else {
            _loginResult.value = "Login Success"
        }
    }
}
