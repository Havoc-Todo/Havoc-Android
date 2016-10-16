package io.havoc.todo.model.service;


import java.util.List;

import io.havoc.todo.model.Task;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import rx.schedulers.Schedulers;

public class HavocService {

    //URL for the server
    private static final String HAVOC_URI = "http://ec2-54-158-62-69.compute-1.amazonaws.com:3000/api/";
    private static HavocService instance;
    private HavocAPI mHavocApi;

    private HavocService() {
        this(HAVOC_URI);
    }

    private HavocService(String baseUrl) {
        //So network calls are async
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mHavocApi = retrofit.create(HavocAPI.class);
    }

    /**
     * Gets the instance of HavocService
     *
     * @return instance of HavocService
     */
    public static HavocService getInstance() {
        if (instance == null) {
            instance = new HavocService();
        }
        return instance;
    }

    /**
     * Creates an instance of HavocService if one does not already exist
     */
    public static void initInstance() {
        if (instance == null) {
            instance = new HavocService();
        }
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
        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("task/create/")
        Observable<List<Object>> createNewTask();

        /**
         * Deletes a specified Task using the taskId
         *
         * @param taskID of the task
         * @return status of transaction
         */
        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("task/delete/{task_id}/")
        Observable<Boolean> deleteTask(@Path("task_id") String taskID);

        /**
         * Updates a Task
         *
         * @return status of whether or not the transaction was successful and the task that was updated
         */
        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("task/update/")
        Observable<List<Object>> updateTask();

        /**
         * Gets all Tasks by a specified User
         *
         * @param userId of the user
         * @return list of all Tasks from the specified User
         */
        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @GET("task/read/{user_id}/")
        Observable<List<Task>> getAllTasks(@Path("user_id") String userId);

//        /**
//         * Gets a specified Task created by a specified user
//         *
//         * @param userId of the user
//         * @param taskId of the Task
//         * @return Task that was requested
//         */
//        @GET("task/read/{user_id}/{task_id}/")
//        Observable<Task> getTask(@Path("user_id") String userId, @Path("task_id") String taskId);
    }
}

