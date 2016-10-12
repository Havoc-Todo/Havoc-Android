package io.havoc.todo.presenter;


import net.grandcentrix.thirtyinch.TiPresenter;

import io.havoc.todo.util.LogUtil;
import io.havoc.todo.view.MainActivityView;

public class MainActivityPresenter extends TiPresenter<MainActivityView> {

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        LogUtil.d("Presenter onWakeUp()");
    }
}
