package com.gogo.gogokull.api


import com.geelong.taxi_driver.Help_and_Support.HelpSupportModel.HelpSupportResponse
import com.geelong.taxi_driver.History.HistoryModel.HistoryResponse
import com.geelong.taxi_driver.History.WeekHistoryModel.WeeklyResponse
import com.geelong.taxi_driver.Login.LoginModels.LoginResponse
import com.geelong.taxi_driver.Notification.NotificationModel.NotificationResponse
import com.geelong.taxi_driver.Profile.ProfileModel.ProfileResponse
import com.geelong.taxi_driver.Profile.ProfileModel.UpdateResouce

//import com.geelong.taxi_driver.Profile.image_uploadModel.UpdateResponse
import com.geelong.taxi_driver.Setting.PrivacyAndPolicy.PrivacyAndPolicyResource
import com.geelong.taxi_driver.Setting.TermAndCondition.TermAndConditionResource
import com.geelong.taxi_driver.fragment.LocationUpdate
import com.geelong.taxi_driver.fragment.OfflineDetaliModel.OffDeatailResponse
import com.geelong.taxi_driver.fragment.OnlineDetailModel.OnlineResponse
import com.geelong.taxi_driver.fragment.RejectTripModel.RejectResponse
import com.geelong.taxi_driver.navigation_drawer.Noti_countModel.NotificationCountResponse
import com.geelong.taxi_driver.Notification.Noti_viewModel.noti_view_Response
import com.geelong.taxi_driver.RideLtaer.RidemoduleApi.ridelaterResponse
import com.geelong.taxi_driver.fragment.Complete_Model.completeResponse
import com.geelong.taxi_driver.fragment.AcceptTripModel.AcceptTripResponse
import com.geelong.taxi_driver.fragment.NoJob.nojonResponse
import com.geelong.taxi_driver.fragment.OtpModel.otpmatchResponse

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

interface APIConfiguration {



    @POST("driver/driverLoginotp")
        @Headers("Content-Type: application/json")
    fun login(
            @Body stringStringHashMap: HashMap<String, String>
        ): Call<LoginResponse>



  @POST("driver/termsconditions")
          fun termcondition(
          ): Call<TermAndConditionResource>


@POST("users/privacypolicy")
          fun ploicyprivacy(
          ): Call<PrivacyAndPolicyResource>

          @POST("driver/drivereditprofile")
          fun profileedit(
              @Body stringStringHashMap: HashMap<String, String>
          ): Call<ProfileResponse>



   @POST("driver/driverlocation")
    @Headers("Content-Type: application/json")
    fun locationUpdate(@Body stringStringHashMap: HashMap<String, String>
   ): Call<LocationUpdate>




    @Multipart
    @POST("driver/editdriprofile")
    fun profileupdate(
        @Part("driver_id") driver_id: RequestBody,
        @Part image: MultipartBody.Part?,
        ): Call<UpdateResouce>

@POST("driver/quickcontact")
    fun helpSupport(

    @Body stringStringHashMap: HashMap<String,String>
    ): Call<HelpSupportResponse>




@POST("driver/todayhistory")
    fun history(

    @Body stringStringHashMap: HashMap<String,String>
    ): Call<HistoryResponse>



@POST("driver/allhistory")
    fun weeklyhistory(

    @Body stringStringHashMap: HashMap<String,String>
    ): Call<WeeklyResponse>






@POST("driver/offlinehistory")
    fun OffDeatail(

    @Body stringStringHashMap: HashMap<String,String>
    ): Call<OffDeatailResponse>

@POST("driver/drivertrip")
 fun OnlineDetail(
    @Body stringStringHashMap: HashMap<String, String>
 ): Call<OnlineResponse>


@POST("driver/accepttrip")
 fun Accept_Trip(

    @Body stringStringHashMap: HashMap<String, String>
 ): Call<AcceptTripResponse>


 @POST("driver/rejecttrip")
  fun reject_Trip(
     @Body stringStringHashMap: HashMap<String, String>
  ): Call<RejectResponse>


 @POST("driver/notification")
  fun notification_list(
     @Body stringStringHashMap: HashMap<String, String>
  ): Call<NotificationResponse>



@POST( "driver/newnotification")
fun count_notification(
    @Body stringStringHashMap: HashMap<String, String>
): Call<NotificationCountResponse>


@POST("driver/viewnotification")
 fun noti_view(
    @Body stringStringHashMap: HashMap<String, String>
 ): Call<noti_view_Response>



@POST("driver/drivertripdata")
 fun ridelater(
    @Body stringStringHashMap: HashMap<String, String>
 ): Call<ridelaterResponse>



 @POST("driver/completetrip")
 fun complete_payment(
     @Body stringStringHashMap: HashMap<String, String>
 ):Call<completeResponse>




 @POST("driver/bookingotpverify")
 fun otp_match(
     @Body stringStringHashMap: HashMap<String, String>
 ):Call<otpmatchResponse>


 @POST("driver/cancelridedriver")
 fun no_job(
     @Body stringStringHashMap: HashMap<String, String>
 ):Call<nojonResponse>





}