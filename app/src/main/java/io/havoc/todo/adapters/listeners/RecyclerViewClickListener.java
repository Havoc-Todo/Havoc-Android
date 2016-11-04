package io.havoc.todo.adapters.listeners;


import android.view.View;

public interface RecyclerViewClickListener {

    /**
     * Click Listener to be called when an item is clicked in a RecyclerView
     *
     * @param v        view
     * @param position of the item in the Adapter
     */
    public void recyclerViewListClicked(View v, int position);
}
