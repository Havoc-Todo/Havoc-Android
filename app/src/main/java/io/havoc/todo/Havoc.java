package io.havoc.todo;

import android.app.Application;

import io.havoc.todo.model.service.HavocService;


public class Havoc extends Application {

    private HavocService mHavocService;

    @Override
    public void onCreate() {
        super.onCreate();

        mHavocService = new HavocService();
    }

    public HavocService getHavocService() {
        return mHavocService;
    }
}
