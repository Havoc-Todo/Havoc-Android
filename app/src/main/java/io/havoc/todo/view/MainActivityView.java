package io.havoc.todo.view;

import net.grandcentrix.thirtyinch.TiView;


public interface MainActivityView extends TiView {

    /**
     * Launches the NewTaskActivity screen
     */
    void launchNewTaskActivity();

    /**
     * Logout of Havoc
     * i.e. remove credentials
     */
    void logout();
}
