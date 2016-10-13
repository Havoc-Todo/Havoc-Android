package io.havoc.todo;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface HavocAPI {
    String URI = "";

    /**
     * Creates a new task
     * @return status of whether or not the transaction was successful and the task that was created
     */
    @POST("api/task/create")
    Observable<List<Object>> createNewTask();

    /**
     * Deletes a specified task using the taskId
     * @param taskID of the task
     * @return status of transaction
     */
    @POST("api/task/delete/{task_id}")
    Observable<Boolean> deleteTask(@Path("task_id") int taskID);

    /**
     * Updates a task
     * @return status of whether or not the transaction was successful and the task that was updated
     */
    @POST("api/task/update")
    Observable<List<Object>> updateTask();

    /**
     * Gets all tasks created by a specified user
     * If a taskId is specified, then this will get that specific task
     * @param USER_ID of the user
     * @param taskId of the task (optional)
     * @return list of all tasks
     */
    @GET("api/task/read/{user_id}/{task_id}")
    Observable<List<Object>> getTasks(@Path("user_id") int USER_ID, @Path("task_id") int taskId);
}
