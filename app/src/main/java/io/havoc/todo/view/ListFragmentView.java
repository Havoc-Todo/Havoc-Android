package io.havoc.todo.view;


import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import io.havoc.todo.model.service.HavocService;

public interface ListFragmentView  extends TiView {

    @DistinctUntilChanged
    void loadTaskList(HavocService havocService);
}
