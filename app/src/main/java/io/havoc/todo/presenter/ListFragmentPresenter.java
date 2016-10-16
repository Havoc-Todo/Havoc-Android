package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.responses.GetAllTasksResponse;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.util.LogUtil;
import io.havoc.todo.view.ListFragmentView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListFragmentPresenter extends TiPresenter<ListFragmentView> {

    private List<Task> mListOfTasks;
    private GetAllTasksResponse mGetAllTasksResponse;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    @Override
    public void onWakeUp() {
        super.onWakeUp();

        if (mListOfTasks == null) {
            loadTaskList();
        } else {
            getView().setTaskList(mListOfTasks);
        }
    }

    /**
     * Generates a list of Tasks
     */
    private void loadTaskList() {
        final String userId = "57a7bd24-ddf0-5c24-9091-ba331e486dc7";

        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().getAllTasks(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(mGetAllTasksResponse -> {
                    this.mGetAllTasksResponse = mGetAllTasksResponse;
                    getView().setTaskList(mGetAllTasksResponse.getTasks());
                }, throwable -> {
                    LogUtil.e(throwable.getCause().getMessage());
                })
        );
    }
}
