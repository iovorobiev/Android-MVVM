package com.iovorobiev.mvvm.core;

import android.content.Intent;
import com.iovorobiev.mvvm.ui.project.list.ProjectListActivity;

public interface INavigation {
    void openSoftwareList();
    void openProjectActivity(int id);
}
