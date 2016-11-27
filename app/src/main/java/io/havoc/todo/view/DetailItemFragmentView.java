package io.havoc.todo.view;

import net.grandcentrix.thirtyinch.TiView;

import io.havoc.todo.model.Task;

public interface DetailItemFragmentView extends TiView {

    /**
     * Setup the Detail View to show info from a given Task
     *
     * @param task to show info for
     */
    void setupDetailView(Task task);
}
