package io.havoc.todo.presenter;


import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import io.havoc.todo.model.PrefKey;
import io.havoc.todo.model.Task;
import io.havoc.todo.model.TaskStatusEnum;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.util.LogUtil;
import io.havoc.todo.util.prefs.AuthSharedPrefs;
import io.havoc.todo.view.NewTaskActivityView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewTaskActivityPresenter extends TiPresenter<NewTaskActivityView> {

    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    private Context context;
    private int numTasksInList = 0;

    public NewTaskActivityPresenter(Context context) {
        this.context = context;
    }

    /**
     * Handles what to do when the New Task button is clicked
     *
     * @param newTask ID for the current user
     */
    public void saveNewTaskButtonClicked(final Task newTask) {
        createTask(newTask);
    }

    /**
     * Handles what to do when the New Task button is clicked
     *
     * @param updatedTask Task that was updated
     */
    public void updateTaskButtonClicked(final Task updatedTask) {
        updateTask(updatedTask);
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

    //FIXME, always returns 0
    public int getNumberOfTasks() {
        final String USER = AuthSharedPrefs.getInstance(context).getString(PrefKey.USER_ID);

        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().getAllTasks(USER)
                .flatMap(response -> Observable.from(response.getTasks()))
                //filter out Tasks that are ARCHIVED or DONE
                .filter(task -> task.getStatus() == TaskStatusEnum.INCOMPLETE)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(list -> {
                    numTasksInList = list.size();
                }, Throwable::printStackTrace)
        );

        return numTasksInList;
    }

    /**
     * Creates a new Task
     *
     * @param newTask Task to create
     */
    private void createTask(final Task newTask) {
        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().createNewTask(newTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(response -> {
                    if (response.status) {
                        LogUtil.v("Create Task success!");
                    }
                }, Throwable::printStackTrace)
        );
    }

    /**
     * Updates an existing Task
     *
     * @param updateTask Task to update
     */
    private void updateTask(final Task updateTask) {
        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().updateTask(updateTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(response -> {
                    if (response.status) {
                        LogUtil.v("Updated Task success!");
                    }
                }, Throwable::printStackTrace)
        );
    }
}
