package io.havoc.todo.view;

import net.grandcentrix.thirtyinch.TiView;


public interface MainActivityView extends TiView {

    /**
     * Launches the NewTaskActivity screen
     */
    void launchNewTaskActivity();

    /**
     * Sorts the list by Priority level
     */
    void sortByPriority();

    /**
     * Logout of Havoc
     * i.e. remove credentials
     */
    void logout();
}
