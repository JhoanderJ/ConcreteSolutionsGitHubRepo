package com.jhoander.concretesolutionsgithubrepo.data

import com.jhoander.concretesolutionsgithubrepo.domain.models.RepositoryData
import retrofit2.Call
import retrofit2.http.GET

interface GetDataServiceApi {

    @GET("pulls")
    fun getDataAllUsers() : Call<List<RepositoryData>>
}