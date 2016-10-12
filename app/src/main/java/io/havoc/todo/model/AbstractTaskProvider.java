package io.havoc.todo.model;


public abstract class AbstractTaskProvider {

    /**
     * Gets the current number of items in the DataProvider
     * @return number of items
     */
    public abstract int getCount();

    /**
     * Get the item at a particular position in the list
     * @param index position of item to get
     * @return Task at this index
     */
    public abstract Task getItem(int index);

    /**
     * Removes an item from the list at a given position
     * @param position index of item to remove
     */
    public abstract void removeItem(int position);

    /**
     * Moves an item from one position to another position in the list
     * @param fromPosition starting position of the item
     * @param toPosition ending position of the item
     */
    public abstract void moveItem(int fromPosition, int toPosition);

    /**
     * Swaps two item's positions
     * @param fromPosition starting position of the item
     * @param toPosition ending position of the item
     */
    public abstract void swapItem(int fromPosition, int toPosition);

    /**
     * Undoes the previous item removal
     * @return position that the item was removed from
     */
    public abstract int undoLastRemoval();


    /**
     * Class for what defines a Task
     */
    public static abstract class Task {
        /**
         * Gets a unique id for the Task
         * @return id of item
         */
        public abstract long getId();

        /**
         * Whether or not this item is the section header
         * @return whether or not this item is the section header
         */
        public abstract boolean isSectionHeader();

        /**
         * Gets the ViewType of this Task
         * @return int of the ViewType
         */
        public abstract int getViewType();

//        /**
//         * Gets the text/title of this Task
//         * @return String of text/title
//         */
//        public abstract String getText();

//        //TODO, see about using this for the Snooze swipe
//        public abstract boolean isPinned();
//        public abstract void setPinned(boolean pinned);
    }
}
