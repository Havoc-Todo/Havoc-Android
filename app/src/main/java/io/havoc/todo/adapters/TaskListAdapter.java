package io.havoc.todo.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionRemoveItem;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.adapters.listeners.RecyclerViewClickListener;
import io.havoc.todo.model.Task;
import io.havoc.todo.model.TaskPriorityEnum;
import io.havoc.todo.presenter.ListFragmentPresenter;
import io.havoc.todo.util.LogUtil;

public class TaskListAdapter
        extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>
        implements DraggableItemAdapter<TaskListAdapter.ViewHolder>,
        SwipeableItemAdapter<TaskListAdapter.ViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private TiPresenter presenter;
    private Context context;
    private List<Task> tasks;

    public TaskListAdapter(TiPresenter presenter, RecyclerViewClickListener itemListener) {
        this.presenter = presenter;
        this.itemListener = itemListener;
        tasks = new ArrayList<>();

        // SwipeableItemAdapter requires stable ID, and also
        // have to implement the getItemId() method appropriately.
        setHasStableIds(true);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_item, parent, false);

        //Set the current context
        context = v.getContext();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Task item = tasks.get(position);

        //set Task name
        holder.mTaskNameText.setText(item.getName());

        //set Task priority and color depending on priority
        TaskPriorityEnum priorityEnum = item.getPriority();
        if (priorityEnum != null && priorityEnum != TaskPriorityEnum.NONE) {
            switch (item.getPriority()) {
                case LOW:
                    //Need to get the current textColorSecondary depending on the theme
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = context.getTheme();
                    theme.resolveAttribute(android.R.attr.textColorSecondary, typedValue, true);
                    int color = typedValue.data;
                    holder.mTaskPriorityText.setTextColor(color);
                    break;
                case MEDIUM:
                    holder.mTaskPriorityText.setTextColor(context.getResources().getColor(R.color.md_yellow_700));
                    break;
                case HIGH:
                    holder.mTaskPriorityText.setTextColor(context.getResources().getColor(R.color.md_red_400));
                    break;
            }
            holder.mTaskPriorityText.setText(priorityEnum.toString());
        }

        // set background resource (target view ID: container)
        final int swipeState = holder.getSwipeStateFlags();

        if ((swipeState & Swipeable.STATE_FLAG_IS_UPDATED) != 0) {
            //TODO, actually handle colors of swipe states?
            //This is the color that the item is set to while swiping
//            int bgResId;

//            if ((swipeState & Swipeable.STATE_FLAG_IS_ACTIVE) != 0) {
//                bgResId = R.drawable.bg_item_swiping_active_state;
//            } else if ((swipeState & Swipeable.STATE_FLAG_SWIPING) != 0) {
//                bgResId = R.drawable.bg_item_swiping_state;
//            } else {
//                bgResId = R.drawable.bg_item_normal_state;
//            }

            holder.mContainer.setBackgroundResource(R.drawable.bg_swipe_item_neutral);
        }

        // set swiping properties
        holder.setSwipeItemHorizontalSlideAmount(0);
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        LogUtil.d("onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")");

        if (fromPosition == toPosition) {
            return;
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public boolean onCheckCanStartDrag(ViewHolder holder, int position, int x, int y) {
        // x, y --- relative from the itemView's top-left
        final View containerView = holder.mContainer;
        final View dragHandleView = holder.mDragHandle;

        final int offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        final int offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);

        return hitTest(dragHandleView, x - offsetX, y - offsetY);
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(ViewHolder holder, int position) {
        // no drag-sortable range specified
        return null;
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return true;
    }

    private boolean hitTest(View v, int x, int y) {
        final int tx = (int) (ViewCompat.getTranslationX(v) + 0.5f);
        final int ty = (int) (ViewCompat.getTranslationY(v) + 0.5f);
        final int left = v.getLeft() + tx;
        final int right = v.getRight() + tx;
        final int top = v.getTop() + ty;
        final int bottom = v.getBottom() + ty;

        return (x >= left) && (x <= right) && (y >= top) && (y <= bottom);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        /**
         * TODO getItemId()
         * Will have to tie in some unique id with a given task; use that to specify tasks
         */
        return tasks.get(position).getTaskId().hashCode();
    }

    @Override
    public int onGetSwipeReactionType(ViewHolder holder, int position, int x, int y) {
        if (onCheckCanStartDrag(holder, position, x, y)) {
            return Swipeable.REACTION_CAN_NOT_SWIPE_BOTH_H;
        } else {
            return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
        }
    }

    @Override
    public void onSetSwipeBackground(ViewHolder holder, int position, int type) {
        int bgRes = 0;
        switch (type) {
            case Swipeable.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND:
                bgRes = R.drawable.bg_swipe_item_neutral;
                break;
            case Swipeable.DRAWABLE_SWIPE_LEFT_BACKGROUND:
                bgRes = R.drawable.bg_swipe_item_left;
                break;
            case Swipeable.DRAWABLE_SWIPE_RIGHT_BACKGROUND:
                bgRes = R.drawable.bg_swipe_item_right;
                break;
        }

        holder.itemView.setBackgroundResource(bgRes);
    }

    @Override
    public SwipeResultAction onSwipeItem(ViewHolder holder, final int position, int result) {
        LogUtil.d("onSwipeItem(position = " + position + ", result = " + result + ")");

        switch (result) {
            //swipe right -- mark as done
            case Swipeable.RESULT_SWIPED_RIGHT:
                return new SwipeRightResultAction(this, position);
            //swipe left -- snooze
            case Swipeable.RESULT_SWIPED_LEFT:
                return new SwipeLeftResultAction(this, position);
            //other --- do nothing
            case Swipeable.RESULT_CANCELED:
            default:
                return null;
        }
    }

    private interface Draggable extends DraggableItemConstants {
        //nothing
    }

    private interface Swipeable extends SwipeableItemConstants {
        //nothing
    }

    /**
     * Action to perform when swiping LEFT on an item
     */
    private static class SwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {
        private final int mPosition;
        private TaskListAdapter mAdapter;

        SwipeLeftResultAction(TaskListAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    /**
     * Action to perform when swiping RIGHT on an item
     */
    private static class SwipeRightResultAction extends SwipeResultActionRemoveItem {
        private final int mPosition;
        private TaskListAdapter mAdapter;

        SwipeRightResultAction(TaskListAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            ((ListFragmentPresenter) mAdapter.presenter).markTaskAsComplete(mAdapter.tasks.get(mPosition));
            mAdapter.tasks.remove(mPosition);
            mAdapter.notifyItemRemoved(mPosition);
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    static class ViewHolder extends AbstractSwipeableItemViewHolder implements View.OnClickListener {
        @BindView(R.id.container)
        FrameLayout mContainer;
        @BindView(android.R.id.text1)
        AppCompatTextView mTaskNameText;
        @BindView(R.id.priority_text)
        AppCompatTextView mTaskPriorityText;
        @BindView(R.id.drag_handle)
        View mDragHandle;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            mContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }

        @Override
        public View getSwipeableContainerView() {
            return mContainer;
        }
    }
}
