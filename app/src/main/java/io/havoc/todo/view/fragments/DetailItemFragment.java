package io.havoc.todo.view.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.model.Task;
import io.havoc.todo.presenter.DetailItemFragmentPresenter;
import io.havoc.todo.util.TiDialogFragment;
import io.havoc.todo.view.DetailItemFragmentView;

public class DetailItemFragment
        extends TiDialogFragment<DetailItemFragmentPresenter, DetailItemFragmentView>
        implements DetailItemFragmentView, View.OnClickListener {

    @BindView(R.id.task_detail_name)
    public AppCompatTextView taskDetailNameText;
    @BindView(R.id.task_detail_description)
    public AppCompatTextView taskDetailDescriptionText;
    @BindView(R.id.task_detail_priority)
    public AppCompatTextView taskDetailPriorityText;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private Task task; //the current Task being detailed

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

        //Get the Json back and parse it as a Task
        task = getPresenter().getTaskFromBundle(getArguments());

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.close);
        }
        setHasOptionsMenu(true);

        taskDetailNameText.setText(task.getName());
        taskDetailDescriptionText.setText(task.getDescription());
        taskDetailPriorityText.setText(task.getPriority().toString());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_edit:
                break;
        }
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
            getPresenter().deleteTask(task);
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
