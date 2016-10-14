package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.view.ListFragmentView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListFragmentPresenter extends TiPresenter<ListFragmentView> {

    private List<Task> mListOfTasks;
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

//        Observable<List<Task>> call = HavocService.getInstance().getHavocAPI().getAllTasks(userId);
//        Subscription subscription = call.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Task>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Task> tasks) {
//                        mListOfTasks = tasks;
//                        getView().setTaskList(mListOfTasks);
//                        LogUtil.v("Task name: " + mListOfTasks.get(0).name);
//                    }
//                });
        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().getAllTasks(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(mListOfTasks -> {
                    this.mListOfTasks = mListOfTasks;
                    getView().setTaskList(mListOfTasks);
//                    LogUtil.v("Task name: " + mListOfTasks.get(0).name);
                })
        );
    }
}
