package com.example.remind.app

sealed class Screens(val route: String) {
    object Splash: Screens("splash") {
        object SplashScreen: Screens("splash_screen")
    }
    object Register: Screens("register") {
        object Login: Screens("login")
        object SelectType: Screens("selecttype")
        object OnBoardingPatience: Screens("patience_onboarding")
        object OnBoardingCheckDoctor: Screens("doctor_1")
        object OnBoardingLoadingDoctor: Screens("doctor_2")
        object OnBoardingCenter: Screens("center_onboarding")
        object OnBoardingFinal: Screens("final_onboarding")
    }

    object Doctor: Screens("doctor") {
        object DoctorMain: Screens("DoctorMain")
        object DoctorPatienceRegister: Screens("PatienceRegister_doctor")
    }
    object Center: Screens("center") {
        object CenterMain: Screens("CenterMain")
        object CenterPatienceRegister: Screens("PatienceRegister_center")
    }
    object Patience: Screens("patience") {
        object Home: Screens("HomeScreen")
        object MoodChart: Screens("MoodChart")
        object Medicine: Screens("Medicine")
        object MyPage: Screens("MyPage")
    }
}
