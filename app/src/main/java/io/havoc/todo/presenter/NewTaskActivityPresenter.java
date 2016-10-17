package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;

import io.havoc.todo.model.Task;
import io.havoc.todo.model.TaskPriorityEnum;
import io.havoc.todo.model.responses.StandardTaskResponse;
import io.havoc.todo.model.service.HavocService;
import io.havoc.todo.util.LogUtil;
import io.havoc.todo.view.NewTaskActivityView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTaskActivityPresenter extends TiPresenter<NewTaskActivityView> {

    private StandardTaskResponse mStandardTaskResponse;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    @Override
    public void onWakeUp() {
        super.onWakeUp();
    }

    public void saveNewTaskButtonClicked(String name, String description, TaskPriorityEnum priority) {
        createTask(name, description, priority);
    }

    private void createTask(String name, String description, TaskPriorityEnum priority) {
        final String USER = "57a7bd24-ddf0-5c24-9091-ba331e486dc7";

        Task newTask = new Task(name, description, "", null, USER, 4, priority, null, null);

        Call<StandardTaskResponse> call = HavocService.getInstance().getHavocAPI().createNewTask(newTask);
        call.enqueue(new Callback<StandardTaskResponse>() {
            @Override
            public void onResponse(Call<StandardTaskResponse> call, Response<StandardTaskResponse> response) {
                LogUtil.v("Response message: " + response.message());
            }

            @Override
            public void onFailure(Call<StandardTaskResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

//        rxHelper.manageSubscription(HavocService.getInstance().getHavocAPI().createNewTask(newTask)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    this.mStandardTaskResponse = response;
//                    LogUtil.v("Status of response: " + mStandardTaskResponse.status);
//                }, throwable -> {
//                    throwable.printStackTrace();
//                })
//        );
    }
}
