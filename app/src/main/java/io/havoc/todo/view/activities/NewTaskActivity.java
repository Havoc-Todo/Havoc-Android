package io.havoc.todo.view.activities;

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

public class NewTaskActivity extends TiActivity<NewTaskActivityPresenter, NewTaskActivityView> implements NewTaskActivityView {

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

        getPresenter().saveNewTaskButtonClicked(taskName, taskDesc, selectedTaskPriority);
        this.finish();
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

        fabSaveTask.setOnClickListener(view -> saveNewTask());

        radioGroupPriorities.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioButtonPriorityLow.getId()) {
                selectedTaskPriority = TaskPriorityEnum.LOW;
            } else if (checkedId == radioButtonPriorityMedium.getId()) {
                selectedTaskPriority = TaskPriorityEnum.MEDIUM;
            } else if (checkedId == radioButtonPriorityHigh.getId()) {
                selectedTaskPriority = TaskPriorityEnum.HIGH;
            } else {
                //todo when nothing is selected
            }
        });
    }
}
