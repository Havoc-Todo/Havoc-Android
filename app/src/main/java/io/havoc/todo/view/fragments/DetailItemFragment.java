package io.havoc.todo.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.model.Task;
import io.havoc.todo.presenter.DetailItemFragmentPresenter;
import io.havoc.todo.view.DetailItemView;

public class DetailItemFragment extends TiFragment<DetailItemFragmentPresenter, DetailItemView>
        implements DetailItemView {

    @BindView(R.id.task_detail_name)
    AppCompatTextView taskDetailNameText;
    @BindView(R.id.task_detail_description)
    AppCompatTextView taskDetailDescriptionText;
    @BindView(R.id.task_detail_priority)
    AppCompatTextView taskDetailPriorityText;

    @NonNull
    @Override
    public DetailItemFragmentPresenter providePresenter() {
        return new DetailItemFragmentPresenter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_item, container, false);
        ButterKnife.bind(this, view);

        //Get the Json back and parse it as a Task
        Bundle bundle = getArguments();
        final String taskAsJson = bundle.getString("task");
        Task task = new Gson().fromJson(taskAsJson, Task.class);

        taskDetailNameText.setText(task.getName());
        taskDetailDescriptionText.setText(task.getDescription());
        taskDetailPriorityText.setText(task.getPriority().toString());

        return view;
    }
}
