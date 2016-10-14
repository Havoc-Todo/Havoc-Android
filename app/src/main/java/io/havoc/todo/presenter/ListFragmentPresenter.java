package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.List;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.view.ListFragmentView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListFragmentPresenter extends TiPresenter<ListFragmentView> {

    private HavocService havocService;
    private List<Task> mListOfTasks;

    public ListFragmentPresenter(HavocService havocService) {
        this.havocService = havocService;
    }

    @Override
    public void onWakeUp() {
        super.onWakeUp();

        if (mListOfTasks == null) {
            loadTaskList(havocService);
        } else {
            getView().setTaskList(mListOfTasks);
        }
    }

    /**
     * Generates a list of Tasks
     *
     * @param havocService Retrofit + HavocAPI needed for transaction to occur
     */
    private void loadTaskList(HavocService havocService) {
        havocService.getHavocAPI()
                .getAllTasks("57a7bd24-ddf0-5c24-9091-ba331e486dc7", "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListOfTasks -> {
                    this.mListOfTasks = mListOfTasks;
                    getView().setTaskList(mListOfTasks);
                });
    }
}
