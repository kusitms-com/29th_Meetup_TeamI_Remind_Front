package com.example.remind.feature.screens.patience.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.example.remind.app.Screens
import com.example.remind.core.base.BaseViewModel
import com.example.remind.data.network.adapter.ApiResult
import com.example.remind.data.network.interceptor.TokenManager
import com.example.remind.domain.usecase.patience_usecase.PatientMedicineDailyUseCase
import com.example.remind.feature.screens.auth.login.LoginContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val patientMedicineDailyUseCase: PatientMedicineDailyUseCase
): BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>(
    initialState = HomeContract.State()
) {
    init {
        viewModelScope.launch {
            val memberId = tokenManager.getUserId().first()
            updateState(
                currentState.copy(
                    memberId = memberId
                )
            )
        }
    }
    override fun reduceState(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.WritingButtonClicked -> {
                navigateToWriting()
            }
            is HomeContract.Event.showSosDialog -> {
                updateState(currentState.copy(sosDialogState = true))
            }
            is HomeContract.Event.DismissDialog -> {
                updateState(currentState.copy(sosDialogState = false))
            }
            is HomeContract.Event.showMediDialog -> {
                updateState(currentState.copy(medicineDialogState = true))
            }
            is HomeContract.Event.dismissMediDialog -> {
                updateState(currentState.copy(medicineDialogState = false))
            }
            is HomeContract.Event.CallButtonClicked -> {
                setSosCall(event.context, event.number)
            }
        }
    }



    fun navigateToWriting() {
        postEffect(
            HomeContract.Effect.NavigateTo(
                destinaton = Screens.Patience.Home.WritingMoodStep1.route,
                navOptions = navOptions {
                    popUpTo(Screens.Patience.Home.route) {
                        inclusive = false
                    }
                }
            )
        )
    }

    fun getUserId() {
        tokenManager.getUserId()
    }

    fun setSosCall( context: Context,  number:String) {
        postEffect(
            HomeContract.Effect.getCall(context, number)
        )
    }

    fun getMedicineDaily(memberId: Int, date: String) {
        viewModelScope.launch {
            val result = patientMedicineDailyUseCase.invoke(memberId, date)
            when(result) {
                is ApiResult.Success -> {
                    val dailyTakingMedicineDtos = result.data?.data?.dailyTakingMedicineDtos ?: emptyList()
                    updateState(currentState.copy(medicineDailyData = dailyTakingMedicineDtos))
                }
                is ApiResult.Failure.UnknownApiError -> {
                    setToastMessage("리마인드 고객센터에 문의하세요")
                }
                is ApiResult.Failure.NetworkError -> {
                    setToastMessage("네트워크 설정을 확인해주세요")
                }
                is ApiResult.Failure.HttpError -> {
                    setToastMessage("api 응답에러 ${result.code}")
                }
            }
        }
    }

    fun setToastMessage(text: String) {
        postEffect(HomeContract.Effect.Toastmessage(text))
    }
}