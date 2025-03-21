package com.example.sportassistant.presentation.ofp_graphic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportassistant.data.repository.OFPResultsRepository
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.enums.AnthropometricParamsMeasures
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.presentation.ant_params_graphic.domain.AnthropometricParamsGraphicUiState
import com.example.sportassistant.presentation.ofp_graphic.domain.OFPResultsGraphicUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class OFPResultsGraphicViewModel(
    private val ofpRepository: OFPResultsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<OFPResultsGraphicUiState>(
        OFPResultsGraphicUiState()
    )
    val uiState: StateFlow<OFPResultsGraphicUiState> = _uiState.asStateFlow()

    private val _getGraphicDataResponse = MutableLiveData<ApiResponse<List<GraphicPoint>?>?>()
    val getGraphicDataResponse = _getGraphicDataResponse

    private val _getCategoriesResponse = MutableLiveData<ApiResponse<List<CategoryModel>?>>()
    val getCategoriesResponse = _getCategoriesResponse

    fun getGraphicData(
        startDate: LocalDate,
        endDate: LocalDate,
        categoryId: UUID
    ) = baseRequest(
        _getGraphicDataResponse
    ) {
        ofpRepository.getGraphicData(
            startDate = startDate,
            endDate = endDate,
            categoryId = categoryId,
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

    fun clearGraphicDataResponse() {
        _getGraphicDataResponse.postValue(null)
    }
}