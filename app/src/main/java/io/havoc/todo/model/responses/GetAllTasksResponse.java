package io.havoc.todo.model.responses;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.havoc.todo.model.Task;

public class GetAllTasksResponse {

    public boolean status;

    @SerializedName("doc")
    public List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }
}
