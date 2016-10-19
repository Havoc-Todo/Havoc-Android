package io.havoc.todo.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import io.havoc.todo.R;
import io.havoc.todo.presenter.DetailItemFragmentPresenter;
import io.havoc.todo.view.DetailItemView;

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
        return inflater.inflate(R.layout.fragment_detail_item, container, false);
    }

}
