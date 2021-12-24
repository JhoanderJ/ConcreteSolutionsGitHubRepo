package com.jhoander.concretesolutionsgithubrepo.remote

import com.jhoander.concretesolutionsgithubrepo.PullsActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private var retrofit: Retrofit? = null
        private var BASE_URL = "https://api.github.com/repos/"

        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                val pullsRecebidos: String = PullsActivity.sendPulls
                BASE_URL += pullsRecebidos
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            } else {
                val pullsRecebidos: String = PullsActivity.sendPulls
                BASE_URL = BASE_URL + pullsRecebidos
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            }
            BASE_URL = "https://api.github.com/repos/"
            return retrofit
        }
    }
}