package io.havoc.todo.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;


public interface MainActivityView extends TiView {

    @CallOnMainThread
    void showText(final String text);
}
