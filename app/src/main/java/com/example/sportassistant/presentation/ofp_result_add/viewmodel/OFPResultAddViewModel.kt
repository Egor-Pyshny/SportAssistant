package com.example.sportassistant.presentation.ofp_result_add.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportassistant.data.repository.OFPResultsRepository
import com.example.sportassistant.data.schemas.ofp_results.requests.OFPResultsCreateRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.presentation.competition_add.domain.CompetitionUiState
import com.example.sportassistant.presentation.ofp_result_add.domain.OFPResultModelUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class OFPResultAddViewModel(
    private val ofpRepository: OFPResultsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<OFPResultModelUiState>(OFPResultModelUiState())
    val uiState: StateFlow<OFPResultModelUiState> = _uiState.asStateFlow()

    private val _ofpAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val ofpAddResponse = _ofpAddResponse

    private val _getCategoriesResponse = MutableLiveData<ApiResponse<List<CategoryModel>?>>()
    val getCategoriesResponse = _getCategoriesResponse

    fun addOFPResult(data: OFPResultsCreateRequest) = baseRequest(
        _ofpAddResponse
    ) {
        ofpRepository.createOFPResult(
            data = data
        )
    }

    fun getCategories() {
        val state = ApplicationState.getState()
        if (state.OFPCategories != null) {
            _getCategoriesResponse.postValue(
                ApiResponse.Success<List<CategoryModel>>(
                    data = state.OFPCategories!!
                )
            )
        } else {
            baseRequest(
                _getCategoriesResponse
            ) {
                ofpRepository.getCategories()
            }
        }
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