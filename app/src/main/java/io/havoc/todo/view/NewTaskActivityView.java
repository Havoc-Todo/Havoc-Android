package io.havoc.todo.view;


import net.grandcentrix.thirtyinch.TiView;

import io.havoc.todo.model.Task;

public interface NewTaskActivityView extends TiView {

    /**
     * Handles leaving the New Task screen and creating a new Task
     */
    void saveNewTask();

    /**
     * Handles leaving the New Task screen and updating an existing Task
     */
    void updateTask();

    /**
     * Setups the fields to be of values from an existing Task
     *
     * @param editTask Task that will be edited
     */
    void setupEditTask(final Task editTask);
}
