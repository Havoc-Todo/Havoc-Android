package io.havoc.todo.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * POJO Subtask
 */
@JsonObject
public class Subtask {

    /**
     * Name of the Subtask
     */
    public String name;

    /**
     * Whether or not the Subtask is completed
     */
    public boolean isCompleted;
}
