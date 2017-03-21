package com.iovorobiev.mvvm.ui.project.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.iovorobiev.mvvm.R;
import com.iovorobiev.mvvm.core.Navigation;
import com.iovorobiev.mvvm.databinding.ProjectsListBinding;
import com.iovorobiev.mvvm.ui.project.ProjectStringResolver;
import org.jetbrains.annotations.NotNull;

public class ProjectListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectsListBinding binding = DataBindingUtil.setContentView(this, R.layout.projects_list);
        ProjectItemViewModel.ParameterStringResolver resolver = new ProjectStringResolver(this);
        binding.setModel(new ProjectListViewModel(resolver, new Navigation(this)));
    }
}
