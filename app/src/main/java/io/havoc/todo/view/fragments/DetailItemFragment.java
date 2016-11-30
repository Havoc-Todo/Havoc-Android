package io.havoc.todo.view.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.model.Task;
import io.havoc.todo.presenter.DetailItemFragmentPresenter;
import io.havoc.todo.util.TiDialogFragment;
import io.havoc.todo.view.DetailItemFragmentView;
import io.havoc.todo.view.activities.NewTaskActivity;

public class DetailItemFragment
        extends TiDialogFragment<DetailItemFragmentPresenter, DetailItemFragmentView>
        implements DetailItemFragmentView, View.OnClickListener {

    @BindView(R.id.task_detail_name)
    public AppCompatTextView taskDetailNameText;
    @BindView(R.id.task_detail_description)
    public AppCompatTextView taskDetailDescriptionText;
    @BindView(R.id.task_detail_priority)
    public AppCompatTextView taskDetailPriorityText;
    @BindView(R.id.fab_edit)
    public FloatingActionButton fabEditTask;
    @BindView(R.id.dial_button)
    public AppCompatButton dialButton;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private Task currentTask; //currently viewed Task
    private String person; //used for fake numbers and fake emailing
    private boolean email;

    @NonNull
    @Override
    public DetailItemFragmentPresenter providePresenter() {
        return new DetailItemFragmentPresenter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Full width/height of screen dialog if the device is a phone
        if (!getResources().getBoolean(R.bool.isTablet)) {
            final int width = ViewGroup.LayoutParams.MATCH_PARENT;
            final int height = ViewGroup.LayoutParams.MATCH_PARENT;

            Dialog dialog = getDialog();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(width, height);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);
        ButterKnife.bind(this, view);

        setupToolbar();
        fabEditTask.setOnClickListener(this);
        dialButton.setOnClickListener(this);

        //Get the Json back and parse it as a Task
        currentTask = getPresenter().getTaskFromBundle(getArguments());
        setupDetailView(currentTask);

        //Can't do calls on a tablet
        ArrayList<String> listOfContacts = new ArrayList<String>() {{
            add("sarah");
            add("dad");
            add("mom");
            add("rachel");
            add("cain");
        }};
        final String taskName = currentTask.getName().toLowerCase();

        for (String s : listOfContacts) {
            if (taskName.contains(s) && (taskName.contains("dial") || taskName.contains("email") || taskName.contains("call"))) {

                person = s;
                if (taskName.contains("call") || taskName.contains("dial") && !getResources().getBoolean(R.bool.isTablet)) {
                    dialButton.setText("Call ".concat(s.toUpperCase()));
                    email = false;
                    dialButton.setVisibility(View.VISIBLE);
                }
                if (taskName.contains("email")) {
                    dialButton.setText("Email ".concat(s.toUpperCase()));
                    email = true;
                    dialButton.setVisibility(View.VISIBLE);
                }
            }
        }

        return view;
    }

    @Override
    public void setupDetailView(Task task) {
        taskDetailNameText.setText(task.getName());
        taskDetailDescriptionText.setText(task.getDescription());
        taskDetailPriorityText.setText(task.getPriority().toString());
    }

    /**
     * Setups the Toolbar and all that jazz
     */
    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.close);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                currentTask = getPresenter().getTaskFromExtras(data, "updatedTask");
                setupDetailView(currentTask);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_edit:
                Intent editActivityIntent = new Intent(getContext(), NewTaskActivity.class);
                editActivityIntent.putExtra("taskToEdit", (new Gson()).toJson(currentTask));
                startActivityForResult(editActivityIntent, 2);
                break;
            case R.id.dial_button:
                if (email) {
                    emailPerson();
                } else {
                    callPerson();
                }
                break;
        }
    }

    private void emailPerson() {
        String email = "";

        switch (person) {
            case "sarah":
                email = "sarahmscott94@gmail.com";
                break;
            case "dad":
                email = "leegantt@gmail.com";
                break;
            case "mom":
                email = "maryjogantt@gmail.com";
                break;
            case "cain":
                email = "wiiareonfire@gmail.com";
                break;
            case "rachel":
                email = "rlgantt@gmail.com";
                break;
        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void callPerson() {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        String number = "tel:";
        switch (person) {
            case "sarah":
                number = number.concat("7703641273");
                break;
            case "dad":
                number = number.concat("7708453849");
                break;
            case "mom":
                number = number.concat("7708550475");
                break;
            case "cain":
                number = number.concat("6789433882");
                break;
            case "rachel":
                number = number.concat("4049554544");
                break;
        }
        dialIntent.setData(Uri.parse(number));
        startActivity(dialIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_detail_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            // handle confirmation button click here
            getPresenter().deleteTask(currentTask);
            dismiss();
            return true;
        } else if (id == android.R.id.home) {
            //closes the dialog
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
