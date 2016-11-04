package io.havoc.todo.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.adapters.TaskListAdapter;
import io.havoc.todo.adapters.listeners.RecyclerViewClickListener;
import io.havoc.todo.model.Task;
import io.havoc.todo.presenter.ListFragmentPresenter;
import io.havoc.todo.view.ListFragmentView;
import io.havoc.todo.view.activities.MainActivity;

public class ListFragment extends TiFragment<ListFragmentPresenter, ListFragmentView>
        implements ListFragmentView,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerViewClickListener {

    @BindView(R.id.rv_task_list)
    public RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefresh;
    private TaskListAdapter mTaskListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewSwipeManager mRecyclerViewSwipeManager;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;

    @NonNull
    @Override
    public ListFragmentPresenter providePresenter() {
        return new ListFragmentPresenter();
    }

    @Override
    public void setTaskList(List<Task> tasks) {
        mTaskListAdapter.setTasks(tasks);
    }

    @Override
    public void setLoading(boolean isLoading) {
        swipeRefresh.setRefreshing(isLoading);
    }

    @Override
    public void onRefresh() {
        getPresenter().loadTaskList();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

        //Put the clicked item into a Bundle for the next fragment to consume
        DialogFragment detailItemFragment = new DetailItemFragment();
        Bundle args = new Bundle();
        args.putString("task", new Gson().toJson(mTaskListAdapter.getItem(position)));
        detailItemFragment.setArguments(args);

        if (!getResources().getBoolean(R.bool.isTablet)) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.task_detail_fragment_container, detailItemFragment)
                    .addToBackStack(null)
                    .commit();
        }

        if (getResources().getBoolean(R.bool.isTablet)) {
            detailItemFragment.show(getActivity().getSupportFragmentManager(), "Detail Item View");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Refresh the list if a new Task was created
        if (((MainActivity) getActivity()).refreshList) {
            ((MainActivity) getActivity()).refreshList = false;
            onRefresh();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_task_list, container, false);
        ButterKnife.bind(this, view);

        swipeRefresh.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
        mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        mRecyclerViewTouchActionGuardManager.setEnabled(true);

        // swipe manager
        mRecyclerViewSwipeManager = new RecyclerViewSwipeManager();

        //adapter
        mTaskListAdapter = new TaskListAdapter(getPresenter(), this);
        mAdapter = mTaskListAdapter;

        // wrap for swiping
        mWrappedAdapter = mRecyclerViewSwipeManager.createWrappedAdapter(mTaskListAdapter);

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Disable the change animation in order to make turning back animation of swiped item works properly.
        animator.setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);

        //TODO, add'l list decor
//        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_h), true));

        // NOTE:
        // The initialization order is very important! This order determines the priority of touch event handling.
        //
        // priority: TouchActionGuard > Swipe > DragAndDrop
        mRecyclerViewTouchActionGuardManager.attachRecyclerView(mRecyclerView);
        mRecyclerViewSwipeManager.attachRecyclerView(mRecyclerView);

        // for debugging
//        animator.setDebug(true);
//        animator.setMoveDuration(2000);
//        animator.setRemoveDuration(2000);
//        mRecyclerViewSwipeManager.setMoveToOutsideWindowAnimationDuration(2000);
//        mRecyclerViewSwipeManager.setReturnToDefaultPositionAnimationDuration(2000);
    }

    @Override
    public void onDestroyView() {
        if (mRecyclerViewSwipeManager != null) {
            mRecyclerViewSwipeManager.release();
            mRecyclerViewSwipeManager = null;
        }

        if (mRecyclerViewTouchActionGuardManager != null) {
            mRecyclerViewTouchActionGuardManager.release();
            mRecyclerViewTouchActionGuardManager = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }
        mAdapter = null;
        mLayoutManager = null;

        super.onDestroyView();
    }

    public void notifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }

    public void notifyItemInserted(int position) {
        mAdapter.notifyItemInserted(position);
        mRecyclerView.scrollToPosition(position);
    }
}