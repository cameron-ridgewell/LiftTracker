package com.lifttracker.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.lifttracker.common.Exercise;
import org.joda.time.DateTime;

/**
 * Created by cameronridgewell on 2/10/15.
 */
public class ServerRequest {

    //http://192.168.56.1:8080 for genymotion NOTE: sometimes http://10.0.3.2:8080
    //private final String ip_address = "http://192.168.56.1:8080" // for genymotion
    //private final String ip_address = "http://10.0.2.2:8080" // for android emulator
    private final String ip_address = "http://lifttracker-wintra.rhcloud.com";
    private final RequestLibrary svc = new Retrofit.Builder()
            .baseUrl(ip_address).addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestLibrary.class);

    private static ExecutorService exec = Executors.newFixedThreadPool(1);

    private static ServerRequest instance = null;

    protected ServerRequest(){};

    public static ServerRequest getInstance() {
        if (instance == null) {
            return new ServerRequest();
        } else {
            return instance;
        }
    }

    private static < E > void execute(Call<E> call, final ResponseAction responseAction)
    {
        call.enqueue(new Callback<E>() {
            @Override
            public void onResponse(Call<E> call, Response<E> response) {
                responseAction.action(response);
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                Log.e("Error", "Call Failure");
            }
        });
    }

    private static < E > void execute(Call<E> serverCall, final ResponseAction responseAction,
                                      final String failureText)
    {
        serverCall.enqueue(new Callback<E>() {
            @Override
            public void onResponse(Call<E> call, Response<E> response) {
                responseAction.action(response);
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                Log.e("Error", failureText);
            }
        });
    }

    public void addExercise(final Exercise exercise, final Context context) {
        Call<RequestLibrary.HttpBinResponse> call = svc.addExercise(exercise);
        execute(call, new ResponseAction() {
            @Override
            public void action(Object input) {
                if (((Response) input).code() == 302)
                {
                    Toast.makeText(context, "Exercise "
                                    + exercise.getName() + " Already Exists",
                            Toast.LENGTH_SHORT).show();
                }
                else if (((Response) input).code() < 400)
                {
                    Toast.makeText(context, "Exercise "
                            + exercise.getName() + " Added",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, "addExercise Call failed");
    }

    public void getAllExercises(final ResponseAction responseAction) {
        Call<List<Exercise>> call = svc.getAllExercises();
        execute(call, responseAction);
    }
}
