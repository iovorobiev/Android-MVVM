package com.iovorobiev.mvvm.ui.project.list;

import com.iovorobiev.mvvm.db.DbApi;
import com.iovorobiev.mvvm.ui.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectsProviderImpl implements ProjectsListAdapter.ProjectsProvider {

    private final ProjectItemViewModel.ParameterStringResolver resolver;
    private final DbApi cacheProvider = new DbApi();
    private List<ProjectItemViewModel> vmData = new ArrayList<>();

    public ProjectsProviderImpl(ProjectItemViewModel.ParameterStringResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public ProjectItemViewModel getAt(int position) {
        return vmData.get(position);
    }

    @Override
    public int projectsCount() {
        return vmData.size();
    }

    @Override
    public void loadData(final ProjectsListAdapter.OnDataLoadedListener listener) {
        cacheProvider.loadProjects(new DbApi.OnDataLoadedListener<ProjectModel>() {
            @Override
            public void dataLoaded(List<ProjectModel> data) {
                for (ProjectModel model : data) {
                    ProjectItemViewModel vm = new ProjectItemViewModel(
                            model,
                            resolver
                    );
                    vmData.add(vm);
                    listener.dataLoaded();
                }
            }
        });
    }
}
