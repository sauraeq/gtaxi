package com.gogo.gogokull.api

class APIUtils {
    companion object{

       private const val BASE_URL = "http://demo.equalinfotech.com/geelong/api/"

       fun getServiceAPI(): APIConfiguration? {
           return  APIClient.getApiClient(BASE_URL)!!.create(APIConfiguration::class.java)
       }

   }

}