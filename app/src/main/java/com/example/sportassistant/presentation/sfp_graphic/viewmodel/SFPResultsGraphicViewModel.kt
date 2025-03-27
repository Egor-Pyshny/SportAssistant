package com.example.sportassistant.presentation.sfp_graphic.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.SFPResultsRepository
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.presentation.sfp_graphic.domain.SFPResultsGraphicUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class SFPResultsGraphicViewModel(
    private val sfpRepository: SFPResultsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<SFPResultsGraphicUiState>(
        SFPResultsGraphicUiState()
    )
    val uiState: StateFlow<SFPResultsGraphicUiState> = _uiState.asStateFlow()

    private val _getCategoriesResponse = MutableLiveData<ApiResponse<List<CategoryModel>?>>()
    val getCategoriesResponse = _getCategoriesResponse

    private val _getGraphicDataResponse = MutableLiveData<ApiResponse<List<GraphicPoint>?>?>()
    val getGraphicDataResponse = _getGraphicDataResponse

    fun clearGraphicDataResponse() {
        _getGraphicDataResponse.postValue(null)
    }

    fun getGraphicData(
        startDate: LocalDate,
        endDate: LocalDate,
        categoryId: UUID
    ) = baseRequest(
        _getGraphicDataResponse
    ) {
        sfpRepository.getGraphicData(
            startDate = startDate,
            endDate = endDate,
            categoryId = categoryId,
        )
    }

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

    fun setStartDate(startDate: LocalDate?) {
        _uiState.update { currentState ->
            currentState.copy(
                startDate = startDate
            )
        }
    }

    fun setEndDate(endDate: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                endDate = endDate
            )
        }
    }

    fun setDatePeriod(datePeriod: String) {
        _uiState.update { currentState ->
            currentState.copy(
                datePeriod = datePeriod
            )
        }
    }

    fun setCategory(category: String) {
        _uiState.update { currentState ->
            currentState.copy(
                category = category
            )
        }
    }

    fun setSelectedCategory(categoryId: UUID?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategoryId = categoryId
            )
        }
    }
}