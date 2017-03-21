package com.iovorobiev.mvvm.core;

import android.content.Context;
import android.content.Intent;
import com.iovorobiev.mvvm.ui.project.list.ProjectListActivity;
import com.iovorobiev.mvvm.ui.project.view.ProjectActivity;

public class Navigation implements INavigation {
    private final Context context;

    public Navigation(Context context) {
        this.context = context;
    }

    @Override
    public void openSoftwareList() {
        Intent softwareListIntent = new Intent(context, ProjectListActivity.class);
        context.startActivity(softwareListIntent);
    }

    @Override
    public void openProjectActivity(int id) {
        Intent projectIntent = new Intent(context, ProjectActivity.class);
        projectIntent.putExtra(ProjectActivity.ID_KEY, id);
        context.startActivity(projectIntent);
    }
}
