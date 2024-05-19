package com.example.remind.data.repository.patient

import com.example.remind.data.model.response.MedicingDailyInfoResponse
import com.example.remind.data.network.adapter.ApiResult
import com.example.remind.data.network.service.PatientService
import javax.inject.Inject

class PatientMedicineRepositoryImpl @Inject constructor(
    private val service: PatientService
): PatientMedicineRepository {
    override suspend fun getMedicineDaily(
        memberId: Int,
        date: String
    ): ApiResult<MedicingDailyInfoResponse> {
        return service.getMedicineDailyInfo(memberId, date)
    }
}