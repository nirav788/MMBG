package com.terapanth.app.interfaces;

/**
 * Created by EbitM9 on 10/9/2017.
 */

import com.terapanth.app.Models.AboutResponce;
import com.terapanth.app.Models.HistoryResponce;
import com.terapanth.app.Models.MMBGTeamResponce;
import com.terapanth.app.Models.MahasabhateamResponce;
import com.terapanth.app.Models.ManagementResponce;
import com.terapanth.app.Models.NewsResponce;
import com.terapanth.app.Models.PravachanResponce;
import com.terapanth.app.Models.PrekshadhyanResponce;
import com.terapanth.app.Models.TPRulesResponce;
import com.terapanth.app.Models.VisionMissionResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

  /*  @FormUrlEncoded
    @POST("login")
    Call<LoginResponce> userLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_token") String device_token);


    @FormUrlEncoded
    @POST("agency_register")
    Call<RegistrationResponce> userRegistration(
            @Field("agency_name") String agency_name,
            @Field("agency_email") String agency_eamil,
            @Field("agency_state") String agency_state,
            @Field("agency_city") String agency_city,
            @Field("agency_zip") String agency_zip,
            @Field("agency_address") String agency_address,
            @Field("agency_password") String agency_password,
            @Field("owner_first_name") String owner_first_name,
            @Field("owner_last_name") String owner_last_name,
            @Field("owner_email") String owner_email,
            @Field("owner_contact") String owner_contact,
            @Field("owner_address") String owner_address,
            @Field("agency_contact") String agency_contact);*/


    @GET("mmbgAboutUs.php")
    Call<AboutResponce> About();

    @GET("vision_mission.php")
    Call<VisionMissionResponce> VisionMission();

    @FormUrlEncoded
    @POST("mmbghistory.php")
    Call<HistoryResponce> History(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("rules.php")
    Call<TPRulesResponce> TPRules(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("teamlist.php")
    Call<MMBGTeamResponce> MMbgRules(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("mmbgmanagement.php")
    Call<ManagementResponce> Management(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("mahasabhateam.php")
    Call<MahasabhateamResponce> Mahashabha(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("news.php")
    Call<NewsResponce> News(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("pravachan.php")
    Call<PravachanResponce> Pravachan(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("letestnews.php")
    Call<NewsResponce> Letestnews(@Field("page_token") int pagetoken);

    @FormUrlEncoded
    @POST("prekshadhyan.php")
    Call<PrekshadhyanResponce> Prekshadhyan(@Field("page_token") int pagetoken);
}