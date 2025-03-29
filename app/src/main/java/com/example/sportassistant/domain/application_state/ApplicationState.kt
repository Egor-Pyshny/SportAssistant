package com.example.sportassistant.domain.application_state

import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.ComprehensiveExamination
import com.example.sportassistant.domain.model.MedExamination
import com.example.sportassistant.domain.model.Note
import com.example.sportassistant.domain.model.OFPResult
import com.example.sportassistant.domain.model.SFPResult
import com.example.sportassistant.domain.model.TrainingCamp
import java.time.LocalDate

object ApplicationState {
    @Volatile private var state: State = State()

    fun getState(): State {
        return state
    }

    fun setSelectedCompetition(competition: Competition) {
        state.competition = competition
    }

    fun setCompetitionDay(day: LocalDate?) {
        state.competitionDay = day
    }

    fun setSelectedCamp(camp: TrainingCamp) {
        state.camp = camp
    }

    fun setCampDay(day: LocalDate?) {
        state.campDay = day
    }

    fun setSelectedOFP(ofpResult: OFPResult) {
        state.OFP = ofpResult
    }

    fun setOFPCategories(categories: List<CategoryModel>) {
        state.OFPCategories = categories
    }

    fun setSelectedNote(note: Note) {
        state.note = note
    }

    fun setSelectedMedExamination(medExamination: MedExamination) {
        state.medExamination = medExamination
    }

    fun setSelectedComprehensiveExamination(comprehensiveExamination: ComprehensiveExamination) {
        state.comprehensiveExamination = comprehensiveExamination
    }

    fun setSelectedSFP(sfpResult: SFPResult) {
        state.SFP = sfpResult
    }

    fun setSFPCategories(categories: List<CategoryModel>) {
        state.SFPCategories = categories
    }

    fun setSelectedAnthropometricParams(anthropometricParams: AnthropometricParams) {
        state.antParams = anthropometricParams
    }

    fun setNoteDate(date: LocalDate) {
        state.noteAddData = date
    }
}

data class State (
    var competition: Competition? = null,
    var competitionDay: LocalDate? = null,

    var comprehensiveExamination: ComprehensiveExamination? = null,

    var medExamination: MedExamination? = null,

    var note: Note? = null,
    var noteAddData: LocalDate? = null,

    var OFP: OFPResult? = null,
    var OFPCategories: List<CategoryModel>? = null,

    var SFP: SFPResult? = null,
    var SFPCategories: List<CategoryModel>? = null,

    var camp: TrainingCamp? = null,
    var campDay: LocalDate? = null,

    var antParams: AnthropometricParams? = null,
)
