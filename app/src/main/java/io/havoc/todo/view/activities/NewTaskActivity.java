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

import com.google.gson.Gson;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.model.PrefKey;
import io.havoc.todo.model.Task;
import io.havoc.todo.model.TaskPriorityEnum;
import io.havoc.todo.model.TaskStatusEnum;
import io.havoc.todo.presenter.NewTaskActivityPresenter;
import io.havoc.todo.util.prefs.AuthSharedPrefs;
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
    private boolean isEditingTask = false; //whether or not we are editing a task

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
        final TaskPriorityEnum taskPriority = (selectedTaskPriority == null)
                ? TaskPriorityEnum.NONE : selectedTaskPriority;
        final String user = AuthSharedPrefs.getInstance(this).getString(PrefKey.GOOGLE_USER_EMAIL);

        final Task newTask = new Task(taskName, taskDesc, "", null, user, 0, taskPriority,
                TaskStatusEnum.INCOMPLETE, null);

        getPresenter().saveNewTaskButtonClicked(newTask);

        //Needed so we know to refresh the list once we return to it
        Intent returnIntent = new Intent();
        returnIntent.putExtra("addedNewTask", true);
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    @Override
    public void updateTask() {
        final String taskName = editTextName.getText().toString();
        final String taskDesc = editTextDescription.getText().toString();
        final TaskPriorityEnum taskPriority = selectedTaskPriority;
        final String user = AuthSharedPrefs.getInstance(this).getString(PrefKey.GOOGLE_USER_EMAIL);

        final Task updatedTask = new Task(taskName, taskDesc, "", null, user, 0, taskPriority,
                TaskStatusEnum.INCOMPLETE, null);

        getPresenter().updateTaskButtonClicked(updatedTask);

        //Needed so we know to refresh the Detail task view when returning
        Intent returnIntent = new Intent();
        returnIntent.putExtra("updatedTask", (new Gson()).toJson(updatedTask));
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
        } else {
            selectedTaskPriority = TaskPriorityEnum.NONE;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);

        setupToolbar();

        radioGroupPriorities.setOnCheckedChangeListener(this);
        toolbar.setNavigationOnClickListener(view -> this.finish());

        //use this activity to also edit Tasks
        final String editTaskObject = getIntent().getStringExtra("task");
        if (editTaskObject != null) {
            setupEditTask(editTaskObject);
        }

        if (isEditingTask) {
            fabSaveTask.setOnClickListener(view -> updateTask());
        } else {
            fabSaveTask.setOnClickListener(view -> saveNewTask());
        }
    }

    /**
     * Setups the Toolbar and all that jazz
     */
    private void setupToolbar() {
        //Set the Toolbar to use Up navigation
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setupEditTask(final String editTaskObject) {
        isEditingTask = true;
        toolbar.setTitle(getString(R.string.toolbar_edit_task_title));
        final Task task = (new Gson()).fromJson(editTaskObject, Task.class);

        if (task.getPriority() == TaskPriorityEnum.HIGH) {
            radioGroupPriorities.check(R.id.radio_button_priority_high);
        } else if (task.getPriority() == TaskPriorityEnum.MEDIUM) {
            radioGroupPriorities.check(R.id.radio_button_priority_medium);
        } else if (task.getPriority() == TaskPriorityEnum.LOW) {
            radioGroupPriorities.check(R.id.radio_button_priority_low);
        } else if (task.getPriority() == TaskPriorityEnum.MEDIUM) {
            radioGroupPriorities.check(R.id.radio_button_priority_none);
        }

        editTextName.setText(task.getName());
        editTextDescription.setText(task.getDescription());
    }
}
