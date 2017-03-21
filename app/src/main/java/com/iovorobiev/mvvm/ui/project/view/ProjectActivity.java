package com.iovorobiev.mvvm.ui.project.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.iovorobiev.mvvm.R;
import com.iovorobiev.mvvm.databinding.ProjectBinding;
import com.iovorobiev.mvvm.db.DbApi;
import com.iovorobiev.mvvm.ui.ProjectModel;
import com.iovorobiev.mvvm.ui.project.ProjectStringResolver;
import com.iovorobiev.mvvm.ui.project.list.ProjectItemViewModel;

public class ProjectActivity extends AppCompatActivity {
    public static final String ID_KEY = "id";
    private ProjectStringResolver resolver;
    private DbApi api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProjectBinding binding = DataBindingUtil.setContentView(this, R.layout.project);

        Intent data = getIntent();
        int id = data.getIntExtra(ID_KEY, -1);

        api = new DbApi();
        resolver = new ProjectStringResolver(this);
        api.loadProject(id, new DbApi.OnObjectLoadedListener<ProjectModel>() {
            @Override
            public void objectLoaded(ProjectModel data) {
                binding.setProject(new ProjectItemViewModel(data, resolver));
            }
        });

    }
}
