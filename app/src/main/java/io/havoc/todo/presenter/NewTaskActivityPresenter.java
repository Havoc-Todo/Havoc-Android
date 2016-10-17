package io.havoc.todo.presenter;


import android.os.AsyncTask;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

//        Gson gson = new Gson();
//        String taskAsString = gson.toJson(newTask);
//        LogUtil.wtf("Task as String: " + taskAsString);
//
//        new PostNewTaskASyncTask().execute(taskAsString);


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

    private class PostNewTaskASyncTask extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection connection = null;

            try {
                url = new URL("http://ec2-54-158-62-69.compute-1.amazonaws.com:3000/api/task/create");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept:", "application/json");
                connection.setRequestProperty("Content-Type:", "application/json");

                connection.setUseCaches (false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(urls[0]);
//                LogUtil.v(urls[0]);
                wr.flush();
                wr.close();

                //Get Response
//                InputStream is = connection.getInputStream();
//                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//                String line;
//                StringBuffer response = new StringBuffer();
//                while((line = rd.readLine()) != null) {
//                    response.append(line);
//                    response.append('\r');
//                }
//                rd.close();

                return "lol";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}
