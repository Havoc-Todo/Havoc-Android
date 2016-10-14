package io.havoc.todo.model.service;


import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.util.List;

import io.havoc.todo.model.Task;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public class HavocService {

    //URL for the server
    private static final String HAVOC_URI = "http://ec2-54-158-62-69.compute-1.amazonaws.com:3000";
    private static HavocService instance;
    private HavocAPI mHavocApi;

    private HavocService() {
        this(HAVOC_URI);
    }

    private HavocService(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();

        mHavocApi = retrofit.create(HavocAPI.class);
    }

    /**
     * Gets the instance of HavocService
     *
     * @return instance of HavocService
     */
    public static HavocService getInstance() {
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
         * @return list of all Tasks from the specified User
         */
        @GET("api/task/read/{user_id}/{task_id}")
        Observable<List<Task>> getAllTasks(@Path("user_id") String userId);

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

