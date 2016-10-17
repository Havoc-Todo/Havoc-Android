package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;

import io.havoc.todo.view.MainActivityView;

public class MainActivityPresenter extends TiPresenter<MainActivityView> {

    /**
     * Event that fires off when the new task button is clicked
     */
    public void newTaskButtonClicked() {
        getView().launchNewTaskActivity();
    }
}
