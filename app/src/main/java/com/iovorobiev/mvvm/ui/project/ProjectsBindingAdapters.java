package com.iovorobiev.mvvm.ui.project;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

public class ProjectsBindingAdapters {

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView view,
                                  RecyclerView.Adapter oldAdapter,
                                  RecyclerView.Adapter adapter) {
        if (adapter == null) {
            return;
        }
        if (oldAdapter != adapter) {
            view.setAdapter(adapter);
        }
    }
}
