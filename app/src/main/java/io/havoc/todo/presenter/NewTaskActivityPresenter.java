package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.TaskPriorityEnum;
import io.havoc.todo.model.TaskStatusEnum;
import io.havoc.todo.model.responses.StandardTaskResponse;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.util.LogUtil;
import io.havoc.todo.view.NewTaskActivityView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewTaskActivityPresenter extends TiPresenter<NewTaskActivityView> {

    private StandardTaskResponse mStandardTaskResponse;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    @Override
    public void onWakeUp() {
        super.onWakeUp();
    }

    public void saveNewTaskButtonClicked(final String user, String name, String description, TaskPriorityEnum priority) {
        createTask(user, name, description, priority);
    }

    /**
     * Creates a new Task
     *
     * @param user        ID for the current user
     * @param name        of this Task
     * @param description for the Task
     * @param priority    of the Task
     */
    private void createTask(final String user, String name, String description, TaskPriorityEnum priority) {
        final Task newTask = new Task(name, description, "", null, user, 0, priority, TaskStatusEnum.INCOMPLETE, null);

        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().createNewTask(newTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(response -> {
                    this.mStandardTaskResponse = response;
                    if (response.status) {
                        LogUtil.v("Response was true!");
                    }
                }, Throwable::printStackTrace)
        );
    }
}
