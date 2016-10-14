package io.havoc.todo;

import android.app.Application;

import io.havoc.todo.model.service.HavocService;


public class Havoc extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initSingletons();
    }

    protected void initSingletons() {
        HavocService.initInstance();
    }
}
