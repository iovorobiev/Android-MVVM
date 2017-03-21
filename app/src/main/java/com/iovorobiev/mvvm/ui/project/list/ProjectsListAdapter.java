package com.iovorobiev.mvvm.ui.project.list;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.iovorobiev.mvvm.R;
import com.iovorobiev.mvvm.core.INavigation;
import com.iovorobiev.mvvm.core.Navigation;
import com.iovorobiev.mvvm.databinding.ProjectListItemBinding;

public class ProjectsListAdapter extends RecyclerView.Adapter<ProjectsListAdapter.ProjectViewHolder> {

    private final INavigation navigation;
    private ProjectsProvider projectsProvider;

    ProjectsListAdapter(ProjectsProvider projectsProvider, INavigation navigation) {
        this.projectsProvider = projectsProvider;
        this.navigation = navigation;
        projectsProvider.loadData(new OnDataLoadedListener() {
            @Override
            public void dataLoaded() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProjectListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.project_list_item,
                parent,
                false);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ProjectViewHolder holder, int position) {
        holder.projectBinding.setProject(projectsProvider.getAt(position));
        holder.projectBinding.setNavigation(navigation);
    }

    @Override
    public int getItemCount() {
        return projectsProvider.projectsCount();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        ProjectListItemBinding projectBinding;

        ProjectViewHolder(ProjectListItemBinding projectBinding) {
            super(projectBinding.getRoot());
            this.projectBinding = projectBinding;
        }
    }

    interface ProjectsProvider {
        ProjectItemViewModel getAt(int position);
        int projectsCount();
        void loadData(OnDataLoadedListener listener);
    }

    interface OnDataLoadedListener {
        void dataLoaded();
    }

}
