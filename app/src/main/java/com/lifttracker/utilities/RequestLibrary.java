package com.lifttracker.utilities;

import com.lifttracker.common.Exercise;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public interface RequestLibrary {

//    @POST("/invitation/")
//    public void invite(@Body Invitation invitation, Callback<String> success);
//
//    @GET("/invitation/?filter=user")
//    public List<Invitation> getUserInvitations(@Query("user_id") String user_id);
//
//    @DELETE("/invitation/")
//    public void deleteInvitation(@Query("invitation_id") String invitation_id,
//                                 Callback<Invitation> success);

    @POST("/exercise/?type=new")
    public Call<Exercise> addExercise(@Body Exercise exercise);
}
