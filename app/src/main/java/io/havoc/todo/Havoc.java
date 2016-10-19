package io.havoc.todo;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import io.havoc.todo.model.service.HavocService;


public class Havoc extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initSingletons();
    }

    protected void initSingletons() {
        HavocService.initInstance();
    }
}
