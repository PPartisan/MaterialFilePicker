package com.nbsp.materialfilepicker.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public class EmptyRecyclerView extends RecyclerView {
    @Nullable
    ViewGroup mEmptyViewGroup;

    public EmptyRecyclerView(Context context) { super(context); }

    public EmptyRecyclerView(Context context, AttributeSet attrs) { super(context, attrs); }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (mEmptyViewGroup != null) {
            mEmptyViewGroup.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
        }
    }

    final @NonNull AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }
    };

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setEmptyView(@Nullable ViewGroup mEmptyViewGroup) {
        this.mEmptyViewGroup = mEmptyViewGroup;
        checkIfEmpty();
    }
}
