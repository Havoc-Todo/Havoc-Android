package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;

import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.view.ListFragmentView;

public class ListFragmentPresenter extends TiPresenter<ListFragmentView> {

    private HavocService havocService;

    public ListFragmentPresenter(HavocService havocService) {
        this.havocService = havocService;
    }

    @Override
    public void onWakeUp() {
        super.onWakeUp();
        getView().loadTaskList(havocService);
    }
}
