package io.havoc.todo.service;


import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public class HavocService {

    //URL for the server
    private static final String HAVOC_URI = "ec2-54-158-62-69.compute-1.amazonaws.com";

    private HavocAPI mHavocApi;

    public HavocService() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(HAVOC_URI)
                .client(okHttpClient)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();

        mHavocApi = restAdapter.create(HavocAPI.class);
    }

    public HavocAPI getHavocAPI() {
        return mHavocApi;
    }

    /**
     * RESTful API for accessing data on the backend server
     */
    interface HavocAPI {

        /**
         * Creates a new task
         *
         * @return status of whether or not the transaction was successful and the task that was created
         */
        @POST("api/task/create")
        Observable<List<Object>> createNewTask();

        /**
         * Deletes a specified task using the taskId
         *
         * @param taskID of the task
         * @return status of transaction
         */
        @POST("api/task/delete/{task_id}")
        Observable<Boolean> deleteTask(@Path("task_id") int taskID);

        /**
         * Updates a task
         *
         * @return status of whether or not the transaction was successful and the task that was updated
         */
        @POST("api/task/update")
        Observable<List<Object>> updateTask();

        /**
         * Gets all tasks created by a specified user
         * If a taskId is specified, then this will get that specific task
         *
         * @param USER_ID of the user
         * @param taskId  of the task (optional)
         * @return list of all tasks
         */
        @GET("api/task/read/{user_id}/{task_id}")
        Observable<List<Object>> getTasks(@Path("user_id") int USER_ID, @Path("task_id") int taskId);
    }
}

