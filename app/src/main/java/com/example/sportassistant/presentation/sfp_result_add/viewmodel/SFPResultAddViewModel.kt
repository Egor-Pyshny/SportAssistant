package com.example.sportassistant.presentation.sfp_result_add.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.SFPResultsRepository
import com.example.sportassistant.data.schemas.sfp_results.requests.SFPResultsCreateRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.presentation.sfp_result_add.domain.SFPResultModelUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class SFPResultAddViewModel(
    private val sfpRepository: SFPResultsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<SFPResultModelUiState>(SFPResultModelUiState())
    val uiState: StateFlow<SFPResultModelUiState> = _uiState.asStateFlow()

    private val _sfpAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val sfpAddResponse = _sfpAddResponse

    private val _getCategoriesResponse = MutableLiveData<ApiResponse<List<CategoryModel>?>>()
    val getCategoriesResponse = _getCategoriesResponse

    fun getCategories(){
        val state = ApplicationState.getState()
        if (state.SFPCategories != null) {
            _getCategoriesResponse.postValue(
                ApiResponse.Success<List<CategoryModel>>(
                    data = state.SFPCategories!!
                )
            )
        } else {
            baseRequest(
                _getCategoriesResponse
            ) {
                sfpRepository.getCategories()
            }
        }
    }

    fun addSFPResult(data: SFPResultsCreateRequest) = baseRequest(
        _sfpAddResponse
    ) {
        sfpRepository.createSFPResult(
            data = data
        )
    }

    fun setDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setCategory(category: UUID) {
        _uiState.update { currentState ->
            currentState.copy(
                categoryId = category
            )
        }
    }

    fun setResult(result: String) {
        _uiState.update { currentState ->
            currentState.copy(
                result = result
            )
        }
    }

    fun setNotes(notes: String) {
        _uiState.update { currentState ->
            currentState.copy(
                notes = notes
            )
        }
    }

    fun setGoals(goals: String) {
        _uiState.update { currentState ->
            currentState.copy(
                goals = goals
            )
        }
    }
}