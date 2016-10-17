package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.TaskStatusEnum;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.view.ListFragmentView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListFragmentPresenter extends TiPresenter<ListFragmentView> {

    final String USER = "57a7bd24-ddf0-5c24-9091-ba331e486dc7";
    private List<Task> mListOfTasks;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    @Override
    public void onWakeUp() {
        super.onWakeUp();

        if (mListOfTasks == null) {
            loadTaskList();
        } else {
            getView().setLoading(false);
            getView().setTaskList(mListOfTasks);
        }
    }

    /**
     * Generates a list of Tasks
     */
    public void loadTaskList() {
        getView().setLoading(true);

        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().getAllTasks(USER)
                .flatMap(response -> Observable.from(response.getTasks()))
                //filter out Tasks that are ARCHIVED or DONE
                .filter(task -> task.getStatus() == TaskStatusEnum.INCOMPLETE)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(list -> {
                    mListOfTasks = list;
                    getView().setTaskList(mListOfTasks);
                    getView().setLoading(false);
                }, throwable -> {
                    getView().setLoading(false);
                    throwable.printStackTrace();
                })
        );
    }

    /**
     * Marks a Task as complete by changing it's status to DONE
     *
     * @param task to mark as DONE
     */
    public void markTaskAsComplete(Task task) {
        task.setStatus(TaskStatusEnum.DONE);

        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().updateTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(response -> {
                    //Do nothing
                }, Throwable::printStackTrace));
    }
}
