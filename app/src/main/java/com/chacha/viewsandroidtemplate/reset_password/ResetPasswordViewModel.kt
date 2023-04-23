package com.chacha.viewsandroidtemplate.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chacha.viewsandroidtemplate.util.Event

class ResetPasswordViewModel : ViewModel() {
    private var _uiState = MutableLiveData<ResetPasswordUiState>()
    val uiState: LiveData<ResetPasswordUiState> =_uiState

    private var _interactions = MutableLiveData<Event<ResetPasswordActions>>()
    val interactions: LiveData<Event<ResetPasswordActions>> = _interactions
}

sealed class ResetPasswordActions{

}

sealed class ResetPasswordUiState{

}