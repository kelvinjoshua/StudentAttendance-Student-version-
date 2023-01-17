package com.bale.Estudent.Network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StudentApiService {
   // @POST("register")
   // suspend fun addTourItem(@Body sample: Sample): Response<>
   @GET("GetEntries")
   suspend fun getRegisteredUnitsByStudentName(@Query("student_name") Student:String)
}