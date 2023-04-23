package com.chacha.viewsandroidtemplate.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.chacha.viewsandroidtemplate.api.ViewApi
import com.chacha.viewsandroidtemplate.util.Event
import com.chacha.viewsandroidtemplate.util.asEvent
import com.chacha.viewsandroidtemplate.util.handleThrowable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class RegisterViewModel(private val api: ViewApi) : ViewModel() {
    private val _uiState = MutableLiveData<RegisterUIState>()
    val uiState : LiveData<RegisterUIState> = _uiState

    private val _interactions = MutableLiveData<Event<RegisterActions>>()
    val interactions: LiveData<Event<RegisterActions>> = _interactions

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _uiState.postValue(RegisterUIState.Error(exception.handleThrowable()))
//        val errorPair = exception.handleThrowable()
    }

    fun login(
        email: String? = "",
        phoneNumber: String? = "",
        password: String
    ){
        _uiState.postValue(RegisterUIState.Loading)
        viewModelScope.launch(exceptionHandler){
            val response = withContext(Dispatchers.IO){
                api.firstTimeLogin(
                    ViewApi.Login(
                        Email = email,
                        PhoneNumber = phoneNumber,
                        Password = password
                    )
                )

            }
            if (response.isSuccessful){
                _interactions.postValue(
                    RegisterActions.Navigate(
                        RegisterFragmentDirections.registerFragmentToHomeFragment()
                    ).asEvent())
            } else if(response.isSuccessful){
                _uiState.postValue(RegisterUIState.Error(response.message()))
            }
        }
    }

    fun firstTimeLogin(
        email: String? = "",
        phoneNumber: String? = "",
        password: String
    ){
        _uiState.postValue(RegisterUIState.Loading)
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
                    RegisterActions.Navigate(
                        RegisterFragmentDirections.registerFragmentToHomeFragment()
                    ).asEvent())
            } else if(response.statusCode == 403){
                _uiState.postValue(RegisterUIState.Error(response.statusMessage))
            }
        }
    }





}


sealed class RegisterActions {
    data class Navigate(val destination: NavDirections) : RegisterActions()
    data class BottomNavigate(val bottomSheetDialogFragment: BottomSheetDialogFragment): RegisterActions()
}

sealed class RegisterUIState{
    object Loading: RegisterUIState()
    data class Error(val message: String): RegisterUIState()
    data class Message(val message: String?): RegisterUIState()
}