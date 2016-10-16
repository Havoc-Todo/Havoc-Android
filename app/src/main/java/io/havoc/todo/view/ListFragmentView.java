package io.havoc.todo.view;


import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import java.util.List;

import io.havoc.todo.model.Task;

public interface ListFragmentView  extends TiView {

    @CallOnMainThread
    void setTaskList(List<Task> tasks);
}
