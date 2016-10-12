/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.havoc.todo.model.impl;

import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.havoc.todo.model.AbstractTaskProvider;

public class AbstractTaskProviderImpl extends AbstractTaskProvider {
    private List<ConcreteTask> mData;
    private ConcreteTask mLastRemovedData;
    private int mLastRemovedPosition = -1;

    public AbstractTaskProviderImpl() {
        final String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        mData = new LinkedList<>();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < aToZ.length(); j++) {
                final long id = mData.size();
                final int viewType = 0;
                final String text = Character.toString(aToZ.charAt(j));
                final int swipeReaction = RecyclerViewSwipeManager.REACTION_CAN_SWIPE_UP | RecyclerViewSwipeManager.REACTION_CAN_SWIPE_DOWN;
                mData.add(new ConcreteTask(id, viewType, text, swipeReaction));
            }
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Task getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mData.get(index);
    }

    @Override
    public int undoLastRemoval() {
        if (mLastRemovedData != null) {
            int insertedPosition;
            if (mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = mData.size();
            }

            mData.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        final ConcreteTask item = mData.remove(fromPosition);

        mData.add(toPosition, item);
        mLastRemovedPosition = -1;
    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        Collections.swap(mData, toPosition, fromPosition);
        mLastRemovedPosition = -1;
    }

    @Override
    public void removeItem(int position) {
        //noinspection UnnecessaryLocalVariable
        final ConcreteTask removedItem = mData.remove(position);

        mLastRemovedData = removedItem;
        mLastRemovedPosition = position;
    }

    private static final class ConcreteTask extends Task {

        private final long mId;
        private final String mText;
        private final int mViewType;
//        private boolean mPinned;

        ConcreteTask(long id, int viewType, String text, int swipeReaction) {
            mId = id;
            mViewType = viewType;
            mText = makeText(id, text, swipeReaction);
        }

        private static String makeText(long id, String text, int swipeReaction) {
            final StringBuilder sb = new StringBuilder();

            sb.append(id);
            sb.append(" - ");
            sb.append(text);

            return sb.toString();
        }

        @Override
        public boolean isSectionHeader() {
            return false;
        }

        @Override
        public int getViewType() {
            return mViewType;
        }

        @Override
        public long getId() {
            return mId;
        }

        @Override
        public String toString() {
            return mText;
        }

//        @Override
//        public String getText() {
//            return mText;
//        }
//
//        @Override
//        public boolean isPinned() {
//            return mPinned;
//        }
//
//        @Override
//        public void setPinned(boolean pinned) {
//            mPinned = pinned;
//        }
    }
}