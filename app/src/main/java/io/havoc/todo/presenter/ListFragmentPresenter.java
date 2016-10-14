package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.view.ListFragmentView;
import rx.schedulers.Schedulers;

public class ListFragmentPresenter extends TiPresenter<ListFragmentView> {

    private HavocService havocService;
    private List<Task> mListOfTasks;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public ListFragmentPresenter(HavocService havocService) {
        this.havocService = havocService;
    }

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
        rxHelper.manageSubscription(havocService.getHavocAPI().getAllTasks("57a7bd24-ddf0-5c24-9091-ba331e486dc7")
                .subscribeOn(Schedulers.newThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(mListOfTasks -> {
                    this.mListOfTasks = mListOfTasks;
                    getView().setTaskList(mListOfTasks);
                })
        );
    }
}
