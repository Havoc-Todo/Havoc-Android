package io.havoc.todo.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import io.havoc.todo.R;
import io.havoc.todo.presenter.DetailItemFragmentPresenter;
import io.havoc.todo.view.DetailItemView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailItemFragment extends TiFragment<DetailItemFragmentPresenter, DetailItemView>
        implements DetailItemView {


    @NonNull
    @Override
    public DetailItemFragmentPresenter providePresenter() {
        return new DetailItemFragmentPresenter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_item, container, false);
    }

}
