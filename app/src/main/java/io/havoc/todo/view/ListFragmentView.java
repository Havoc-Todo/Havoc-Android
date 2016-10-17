package io.havoc.todo.view;


import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import java.util.List;

import io.havoc.todo.model.Task;

public interface ListFragmentView extends TiView {

    /**
     * Sets the RecyclerView's Adapter to display the List of Tasks
     *
     * @param tasks to display in the RecyclerView
     */
    @CallOnMainThread
    void setTaskList(List<Task> tasks);

    /**
     * Sets the loading animation
     *
     * @param isLoading whether or not to animate loading
     */
    @CallOnMainThread
    void setLoading(boolean isLoading);
}
