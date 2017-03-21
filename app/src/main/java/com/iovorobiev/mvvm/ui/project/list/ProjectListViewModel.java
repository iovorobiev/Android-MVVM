package com.iovorobiev.mvvm.ui.project.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.iovorobiev.mvvm.core.INavigation;
import com.iovorobiev.mvvm.core.Navigation;

public class ProjectListViewModel {
    public ProjectsListAdapter adapter;

    public ProjectListViewModel(ProjectItemViewModel.ParameterStringResolver resolver,
                                INavigation navigation) {
        adapter = new ProjectsListAdapter(new ProjectsProviderImpl(resolver), navigation);
    }
}
