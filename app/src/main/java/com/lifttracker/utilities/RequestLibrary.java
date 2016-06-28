package com.lifttracker.utilities;

import com.lifttracker.common.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.ResponseBody;
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

    /**
     * Generic HttpBin.org Response Container
     */
    static class HttpBinResponse {
        // the request url
        String url;

        // the requester ip
        String origin;

        // all headers that have been sent
        Map headers;

        // url arguments
        Map args;

        // post form parameters
        Map form;

        // post body json
        Map json;
    }

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
    public Call<HttpBinResponse> addExercise(@Body Exercise exercise);

    @GET("/exercise/?filter=none")
    public Call<List<Exercise>> getAllExercises();
}
