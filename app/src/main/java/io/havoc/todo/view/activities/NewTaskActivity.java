package io.havoc.todo.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.model.TaskPriorityEnum;
import io.havoc.todo.presenter.NewTaskActivityPresenter;
import io.havoc.todo.view.NewTaskActivityView;

public class NewTaskActivity extends TiActivity<NewTaskActivityPresenter, NewTaskActivityView> implements NewTaskActivityView, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.input_task_name)
    public EditText editTextName;
    @BindView(R.id.input_task_description)
    public EditText editTextDescription;
    @BindView(R.id.radio_group_priorities)
    public RadioGroup radioGroupPriorities;
    @BindView(R.id.radio_button_priority_low)
    public RadioButton radioButtonPriorityLow;
    @BindView(R.id.radio_button_priority_medium)
    public RadioButton radioButtonPriorityMedium;
    @BindView(R.id.radio_button_priority_high)
    public RadioButton radioButtonPriorityHigh;
    @BindView(R.id.fab_save)
    FloatingActionButton fabSaveTask;

    private TaskPriorityEnum selectedTaskPriority;

    @NonNull
    @Override
    public NewTaskActivityPresenter providePresenter() {
        return new NewTaskActivityPresenter();
    }

    @Override
    public void saveNewTask() {
        final String taskName = editTextName.getText().toString();
        final String taskDesc = editTextDescription.getText().toString();

        //If there was no selected priority, pass in TaskPriorityEnum.NONE
        getPresenter().saveNewTaskButtonClicked(taskName, taskDesc,
                (selectedTaskPriority == null) ? TaskPriorityEnum.NONE : selectedTaskPriority);

        //Needed so we know to refresh the list once we return to it
        Intent returnIntent = new Intent();
        returnIntent.putExtra("addedNewTask", true);
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == radioButtonPriorityLow.getId()) {
            selectedTaskPriority = TaskPriorityEnum.LOW;
        } else if (checkedId == radioButtonPriorityMedium.getId()) {
            selectedTaskPriority = TaskPriorityEnum.MEDIUM;
        } else if (checkedId == radioButtonPriorityHigh.getId()) {
            selectedTaskPriority = TaskPriorityEnum.HIGH;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);

        //Set the Toolbar to use Up navigation
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> this.finish());

        radioGroupPriorities.setOnCheckedChangeListener(this);

        fabSaveTask.setOnClickListener(view -> saveNewTask());
    }
}
