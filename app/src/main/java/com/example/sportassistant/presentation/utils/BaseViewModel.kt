package com.example.sportassistant.presentation.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable

open class BaseViewModel : ViewModel() {
    private var mJob: Job? = null

    protected fun <T> baseRequest(liveData: MutableLiveData<T>,request: () -> Flow<T>) {
        mJob = viewModelScope.launch(Dispatchers.IO){
            request().collect {
                withContext(Dispatchers.Main) {
                    liveData.value = it
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }

    fun cancelRequest() {
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}