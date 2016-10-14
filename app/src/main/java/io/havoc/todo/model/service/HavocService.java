package io.havoc.todo.model.service;


import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.IOException;
import java.util.List;

import io.havoc.todo.model.Task;
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HAVOC_URI)
                .client(okHttpClient)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();

        mHavocApi = retrofit.create(HavocAPI.class);
    }

    public HavocAPI getHavocAPI() {
        return mHavocApi;
    }

    /**
     * RESTful API for accessing data on the backend server
     */
    public interface HavocAPI {

        /**
         * Creates a new Task
         *
         * @return status of whether or not the transaction was successful and the task that was created
         */
        @POST("api/task/create")
        Observable<List<Object>> createNewTask();

        /**
         * Deletes a specified Task using the taskId
         *
         * @param taskID of the task
         * @return status of transaction
         */
        @POST("api/task/delete/{task_id}")
        Observable<Boolean> deleteTask(@Path("task_id") String taskID);

        /**
         * Updates a Task
         *
         * @return status of whether or not the transaction was successful and the task that was updated
         */
        @POST("api/task/update")
        Observable<List<Object>> updateTask();

        /**
         * Gets all Tasks by a specified User
         *
         * @param userId of the user
         * @param taskId of the Task
         * @return list of all Tasks from the specified User
         */
        @GET("api/task/read/{user_id}/{task_id}")
        Observable<Task> getAllTasks(@Path("user_id") String userId, @Path("task_id") String taskId);

//        /**
//         * Gets a specified Task created by a specified user
//         *
//         * @param userId of the user
//         * @param taskId of the Task
//         * @return Task that was requested
//         */
//        @GET("api/task/read/{user_id}/{task_id}")
//        Observable<Task> getTask(@Path("user_id") String userId, @Path("task_id") String taskId);
    }
}

