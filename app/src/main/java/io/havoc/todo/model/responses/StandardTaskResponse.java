package io.havoc.todo.model.responses;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.havoc.todo.model.Task;

public class StandardTaskResponse {

    /**
     * Whether or not there was an error with the response
     */
    public boolean status;

    /**
     * List of Tasks
     */
    @SerializedName("doc")
    public List<Task> tasks;

    /**
     * Gets the array of Tasks from the response
     *
     * @return the List of Tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }
}
