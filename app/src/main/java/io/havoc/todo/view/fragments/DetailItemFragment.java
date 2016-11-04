package io.havoc.todo.view.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.model.Task;

//public class DetailItemFragment extends TiFragment<DetailItemFragmentPresenter, DetailItemView>
//        implements DetailItemView {

public class DetailItemFragment extends AppCompatDialogFragment {

    @BindView(R.id.task_detail_name)
    AppCompatTextView taskDetailNameText;
    @BindView(R.id.task_detail_description)
    AppCompatTextView taskDetailDescriptionText;
    @BindView(R.id.task_detail_priority)
    AppCompatTextView taskDetailPriorityText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);
        ButterKnife.bind(this, view);

        //Get the Json back and parse it as a Task
        Bundle bundle = getArguments();
        final String taskAsJson = bundle.getString("task");
        Task task = new Gson().fromJson(taskAsJson, Task.class);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_detail_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            // handle confirmation button click here
            return true;
        } else if (id == android.R.id.home) {
            //closes the dialog
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
