package com.mbs.workoutplan.di

import com.mbs.workoutplan.data.auth.repository.AuthSignInRepository
import com.mbs.workoutplan.data.auth.repository.AuthSignInRepositoryImpl
import com.mbs.workoutplan.data.db.repository.UserInfoRepository
import com.mbs.workoutplan.data.db.repository.UserInfoRepositoryImpl
import com.mbs.workoutplan.data.db.repository.WorkoutRepository
import com.mbs.workoutplan.data.db.repository.WorkoutRepositoryImpl
import com.mbs.workoutplan.domain.usecase.CreateNewExerciseUseCase
import com.mbs.workoutplan.domain.usecase.CreateWorkoutUseCase
import com.mbs.workoutplan.domain.usecase.DeleteExerciseUseCase
import com.mbs.workoutplan.domain.usecase.DeleteWorkoutUseCase
import com.mbs.workoutplan.domain.usecase.GetUserInfoUseCase
import com.mbs.workoutplan.domain.usecase.GetUserWorkoutsUseCase
import com.mbs.workoutplan.domain.usecase.GetWorkoutDetailsUseCase
import com.mbs.workoutplan.domain.usecase.SignInWithEmailAndPasswordUseCase
import com.mbs.workoutplan.domain.usecase.UpdateUserInfoUseCase
import com.mbs.workoutplan.domain.usecase.UploadPhotoUseCase
import com.mbs.workoutplan.domain.usecase.UploadProfilePictureUseCase
import com.mbs.workoutplan.presentation.viewmodels.CreateWorkoutViewModel
import com.mbs.workoutplan.presentation.viewmodels.EditProfileViewModel
import com.mbs.workoutplan.presentation.viewmodels.HomeViewModel
import com.mbs.workoutplan.presentation.viewmodels.LoginViewModel
import com.mbs.workoutplan.presentation.viewmodels.NewExerciseViewModel
import com.mbs.workoutplan.presentation.viewmodels.UserInfoViewModel
import com.mbs.workoutplan.presentation.viewmodels.WorkoutDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { UserInfoViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { CreateWorkoutViewModel(get()) }
    viewModel { WorkoutDetailsViewModel(get(), get(), get()) }
    viewModel { NewExerciseViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get()) }
}
val useCaseModule = module {
    single { SignInWithEmailAndPasswordUseCase(get()) }
    single { GetUserInfoUseCase(get()) }
    single { GetUserWorkoutsUseCase(get()) }
    single { CreateWorkoutUseCase(get()) }
    single { GetWorkoutDetailsUseCase(get()) }
    single { UploadPhotoUseCase(get()) }
    single { CreateNewExerciseUseCase(get()) }
    single { DeleteExerciseUseCase(get()) }
    single { UploadProfilePictureUseCase(get()) }
    single { UpdateUserInfoUseCase(get()) }
    single { DeleteWorkoutUseCase(get()) }
}
val repositoryModule = module {
    single<AuthSignInRepository> { AuthSignInRepositoryImpl() }
    single<WorkoutRepository> { WorkoutRepositoryImpl() }
    single<UserInfoRepository> { UserInfoRepositoryImpl() }
}