package com.chacha.viewsandroidtemplate.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.chacha.viewsandroidtemplate.api.ViewApi
import com.chacha.viewsandroidtemplate.register.RegisterActions
import com.chacha.viewsandroidtemplate.register.RegisterFragmentDirections
import com.chacha.viewsandroidtemplate.register.RegisterUIState
import com.chacha.viewsandroidtemplate.util.Event
import com.chacha.viewsandroidtemplate.util.asEvent
import com.chacha.viewsandroidtemplate.util.handleThrowable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginViewModel(private val api: ViewApi) : ViewModel() {
    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState : LiveData<LoginUiState> = _uiState

    private val _interactions = MutableLiveData<Event<LoginActions>>()
    val interactions: LiveData<Event<LoginActions>> = _interactions

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _uiState.postValue(LoginUiState.Error(exception.handleThrowable()))
//        val errorPair = exception.handleThrowable()
    }

    fun firstTimeLogin(
        email: String? = "",
        phoneNumber: String? = "",
        password: String
    ){
        _uiState.postValue(LoginUiState.Loading)
        viewModelScope.launch(exceptionHandler){
            val response = withContext(Dispatchers.IO){
                api.firstTimerLogin(
                    ViewApi.Login(
                        Email = email,
                        PhoneNumber = phoneNumber,
                        Password = password
                    )
                )
            }
            if (response.statusCode ==200){
                _interactions.postValue(
                    LoginActions.Navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    ).asEvent())
            } else if(response.statusCode == 403){
                _uiState.postValue(LoginUiState.Error(response.statusMessage))
            }
        }
    }


}


sealed class LoginActions {
    data class Navigate(val destination: NavDirections) : LoginActions()
    data class BottomNavigate(val bottomSheetDialogFragment: BottomSheetDialogFragment): LoginActions()
}

sealed class LoginUiState{
    object Loading: LoginUiState()
    data class Error(val message: String): LoginUiState()
    data class Message(val message: String?): LoginUiState()
}