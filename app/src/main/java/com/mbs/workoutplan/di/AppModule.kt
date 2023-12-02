package com.mbs.workoutplan.di

import com.mbs.workoutplan.data.auth.repository.AuthSignInRepository
import com.mbs.workoutplan.data.auth.repository.AuthSignInRepositoryImpl
import com.mbs.workoutplan.data.db.repository.WorkoutRepository
import com.mbs.workoutplan.data.db.repository.WorkoutRepositoryImpl
import com.mbs.workoutplan.domain.usecase.GetUserInfoUseCase
import com.mbs.workoutplan.domain.usecase.SignInWithEmailAndPasswordUseCase
import com.mbs.workoutplan.presentation.viewmodels.LoginViewModel
import com.mbs.workoutplan.presentation.viewmodels.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { UserInfoViewModel(get()) }
}
val useCaseModule = module {
    single { SignInWithEmailAndPasswordUseCase(get()) }
    single { GetUserInfoUseCase(get()) }
}
val repositoryModule = module {
    single<AuthSignInRepository> { AuthSignInRepositoryImpl() }
    single<WorkoutRepository> { WorkoutRepositoryImpl() }
}