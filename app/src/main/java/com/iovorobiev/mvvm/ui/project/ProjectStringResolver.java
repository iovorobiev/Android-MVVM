package com.iovorobiev.mvvm.ui.project;

import android.content.Context;
import com.iovorobiev.mvvm.R;
import com.iovorobiev.mvvm.ui.project.list.ProjectItemViewModel;

public class ProjectStringResolver implements ProjectItemViewModel.ParameterStringResolver {

    private final Context context;

    public ProjectStringResolver(Context context) {
        this.context = context;
    }

    @Override
    public String getForPrice(boolean cheap) {
        return context.getString(cheap ? R.string.cheap : R.string.expensive);
    }

    @Override
    public String getForSpeed(boolean fast) {
        return context.getString(fast ? R.string.fast : R.string.slow);
    }

    @Override
    public String getForStability(boolean stable) {
        return context.getString(stable ? R.string.stable : R.string.buggy);
    }
}
