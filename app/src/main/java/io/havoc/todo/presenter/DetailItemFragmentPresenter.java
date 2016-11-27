package io.havoc.todo.presenter;


import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.view.DetailItemFragmentView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailItemFragmentPresenter extends TiPresenter<DetailItemFragmentView> {

    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    /**
     * Gets a Task from a specified Bundle
     *
     * @param bundle to get Task from
     * @return the Task that was retrieved
     */
    public Task getTaskFromBundle(Bundle bundle) {
        final String taskAsJson = bundle.getString("task");
        return (new Gson().fromJson(taskAsJson, Task.class));
    }

    /**
     * Gets a Task from an Intent's extras
     *
     * @param data      Intent to get the data from
     * @param extraName name of the extra
     * @return the Task that was retrieved
     */
    public Task getTaskFromExtras(Intent data, String extraName) {
        return (new Gson()).fromJson(data.getStringExtra(extraName), Task.class);
    }


    /**
     * Deletes a Task
     *
     * @param task to delete
     */
    public void deleteTask(final Task task) {
        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().deleteTask(task.getTaskId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(x -> {
                    //Do nothing
                }, Throwable::printStackTrace)
        );
    }
}
