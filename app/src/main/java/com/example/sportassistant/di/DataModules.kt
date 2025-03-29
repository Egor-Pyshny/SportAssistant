package com.example.sportassistant.di

import com.example.sportassistant.data.repository.AnthropometricParamsRepository
import com.example.sportassistant.data.repository.AuthRepository
import com.example.sportassistant.data.repository.CalendarRepository
import com.example.sportassistant.data.repository.CoachRepository
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.data.repository.ComprehensiveExaminationRepository
import com.example.sportassistant.data.repository.MedExaminationRepository
import com.example.sportassistant.data.repository.NotesRepository
import com.example.sportassistant.data.repository.OFPResultsRepository
import com.example.sportassistant.data.repository.SFPResultsRepository
import com.example.sportassistant.data.repository.TrainingCampsRepository
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.ant_params.viewmodel.AnthropometricParamsViewModel
import com.example.sportassistant.presentation.ant_params_add.viewmodel.AnthropometricParamsAddViewModel
import com.example.sportassistant.presentation.ant_params_graphic.viewmodel.AnthropometricParamsGraphicViewModel
import com.example.sportassistant.presentation.ant_params_info.viewmodel.AnthropometricParamsInfoViewModel
import com.example.sportassistant.presentation.applayout.viewmodel.AppLayoutViewModel
import com.example.sportassistant.presentation.calendar.viewmodel.CalendarViewModel
import com.example.sportassistant.presentation.competition_add.viewmodel.CompetitionAddViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_day.viewmodel.CompetitionDayViewModel
import com.example.sportassistant.presentation.competition_info.viewmodel.CompetitionInfoViewModel
import com.example.sportassistant.presentation.competition_result.viewmodel.CompetitionResultViewModel
import com.example.sportassistant.presentation.comprehensive_examination.viewmodel.ComprehensiveExaminationViewModel
import com.example.sportassistant.presentation.comprehensive_examination_add.viewmodel.ComprehensiveExaminationAddViewModel
import com.example.sportassistant.presentation.comprehensive_examination_info.viewmodel.ComprehensiveExaminationInfoViewModel
import com.example.sportassistant.presentation.login.viewmodel.LogInViewModel
import com.example.sportassistant.presentation.med_examination.ui.MedExaminationScreen
import com.example.sportassistant.presentation.med_examination.viewmodel.MedExaminationViewModel
import com.example.sportassistant.presentation.med_examination_add.viewmodel.MedExaminationAddViewModel
import com.example.sportassistant.presentation.med_examination_info.viewmodel.MedExaminationInfoViewModel
import com.example.sportassistant.presentation.note_add.viewmodel.NotesAddViewModel
import com.example.sportassistant.presentation.note_info.viewmodel.NotesInfoViewModel
import com.example.sportassistant.presentation.notes.viewmodel.NotesViewModel
import com.example.sportassistant.presentation.ofp_graphic.viewmodel.OFPResultsGraphicViewModel
import com.example.sportassistant.presentation.ofp_result_add.viewmodel.OFPResultAddViewModel
import com.example.sportassistant.presentation.ofp_results.viewmodel.OFPResultsViewModel
import com.example.sportassistant.presentation.ofp_results_info.viewmodel.OFPResultInfoViewModel
import com.example.sportassistant.presentation.profile.viewmodel.ProfileInfoViewModel
import com.example.sportassistant.presentation.registration.viewmodel.CheckEmailViewModel
import com.example.sportassistant.presentation.registration.viewmodel.CoachViewModel
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.registration.viewmodel.SetProfileInfoViewModel
import com.example.sportassistant.presentation.reset_password.viewmodel.ResetPasswordViewModel
import com.example.sportassistant.presentation.sfp_graphic.viewmodel.SFPResultsGraphicViewModel
import com.example.sportassistant.presentation.sfp_result_add.viewmodel.SFPResultAddViewModel
import com.example.sportassistant.presentation.sfp_results.viewmodel.SFPResultsViewModel
import com.example.sportassistant.presentation.sfp_results_info.viewmodel.SFPResultInfoViewModel
import com.example.sportassistant.presentation.trainig_camps_add.viewmodel.TrainingCampAddViewModel
import com.example.sportassistant.presentation.training_camp_info.viewmodel.TrainingCampInfoViewModel
import com.example.sportassistant.presentation.training_camps_calendar.viewmodel.TrainingCampsViewModel
import com.example.sportassistant.presentation.training_camps_day.viewmodel.TrainingCampDayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModules = module {
    single {
        UserPreferencesRepository(
            androidContext(),
            get()
        )
    }
    single {
        WindowSizeProvider()
    }
    viewModel{
        AppLayoutViewModel(
            userPreferencesRepository = get(),
            appDispatchers = get()
        )
    }
    factory { AuthRepository(get()) }
    factory { UserRepository(get()) }
    factory { CoachRepository(get()) }
    factory { CompetitionRepository(get()) }
    factory { TrainingCampsRepository(get()) }
    factory { OFPResultsRepository(get()) }
    factory { SFPResultsRepository(get()) }
    factory { AnthropometricParamsRepository(get()) }
    factory { NotesRepository(get()) }
    factory { MedExaminationRepository(get()) }
    factory { ComprehensiveExaminationRepository(get()) }
    factory { CalendarRepository(get()) }
    viewModel{
        LogInViewModel(
            authRepository = get(),
        )
    }
    viewModel{
        ResetPasswordViewModel(
            authRepository = get(),
        )
    }
    viewModel{
        CheckEmailViewModel(
            userRepository = get(),
        )
    }
    viewModel{
        SetProfileInfoViewModel(
            userRepository = get(),
        )
    }
    viewModel{
        RegistrationViewModel(
            authRepository = get(),
        )
    }
    viewModel{
        CoachViewModel(
            coachRepository = get(),
        )
    }
    viewModel{
        ProfileInfoViewModel(
            userRepository = get(),
        )
    }
    viewModel{
        CompetitionViewModel(
            competitionRepository = get(),
        )
    }
    viewModel{
        CompetitionAddViewModel(
            competitionRepository = get(),
        )
    }
    viewModel{
        CompetitionInfoViewModel(
            competitionRepository = get(),
        )
    }
    viewModel{
        CompetitionResultViewModel(
            competitionRepository = get(),
        )
    }
    viewModel{
        CompetitionDayViewModel(
            competitionRepository = get(),
        )
    }
    viewModel{
        TrainingCampsViewModel(
            trainingCampsRepository = get(),
        )
    }
    viewModel{
        TrainingCampAddViewModel(
            trainingCampsRepository = get(),
        )
    }
    viewModel{
        TrainingCampInfoViewModel(
            trainingCampsRepository = get(),
        )
    }
    viewModel{
        TrainingCampDayViewModel(
            trainingCampsRepository = get(),
        )
    }
    viewModel{
        OFPResultsViewModel(
            ofpRepository = get(),
        )
    }
    viewModel{
        OFPResultAddViewModel(
            ofpRepository = get(),
        )
    }
    viewModel{
        OFPResultInfoViewModel(
            ofpRepository = get(),
        )
    }
    viewModel{
        OFPResultsGraphicViewModel(
            ofpRepository = get(),
        )
    }
    viewModel{
        SFPResultsViewModel(
            sfpRepository = get(),
        )
    }
    viewModel{
        SFPResultAddViewModel(
            sfpRepository = get(),
        )
    }
    viewModel{
        SFPResultInfoViewModel(
            sfpRepository = get(),
        )
    }
    viewModel{
        SFPResultsGraphicViewModel(
            sfpRepository = get(),
        )
    }
    viewModel{
        AnthropometricParamsViewModel(
            anthropometricParamsRepository = get(),
        )
    }
    viewModel{
        AnthropometricParamsAddViewModel(
            anthropometricParamsRepository = get(),
        )
    }
    viewModel{
        AnthropometricParamsInfoViewModel(
            anthropometricParamsRepository = get(),
        )
    }
    viewModel{
        AnthropometricParamsGraphicViewModel(
            anthropometricParamsRepository = get(),
        )
    }
    viewModel{
        NotesViewModel(
            notesRepository = get(),
        )
    }
    viewModel{
        NotesAddViewModel(
            notesRepository = get(),
        )
    }
    viewModel{
        NotesInfoViewModel(
            notesRepository = get(),
        )
    }
    viewModel{
        MedExaminationViewModel(
            medExaminationRepository = get(),
        )
    }
    viewModel{
        MedExaminationAddViewModel(
            medExaminationRepository = get(),
        )
    }
    viewModel{
        MedExaminationInfoViewModel(
            medExaminationRepository = get(),
        )
    }
    viewModel{
        ComprehensiveExaminationViewModel(
            comprehensiveExaminationRepository = get(),
        )
    }
    viewModel{
        ComprehensiveExaminationAddViewModel(
            comprehensiveExaminationRepository = get(),
        )
    }
    viewModel{
        ComprehensiveExaminationInfoViewModel(
            comprehensiveExaminationRepository = get(),
        )
    }
    viewModel{
        CalendarViewModel(
            calendarRepository = get(),
        )
    }
}