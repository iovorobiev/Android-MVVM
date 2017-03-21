package com.iovorobiev.mvvm.ui.project.create;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.iovorobiev.mvvm.R;
import com.iovorobiev.mvvm.core.Navigation;
import com.iovorobiev.mvvm.databinding.CreateProjectBinding;

public class CreateProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateProjectBinding binding = DataBindingUtil.setContentView(this, R.layout.create_project);
        binding.setModel(new CreateProjectViewModel(new Navigation(this), new CreateProjectViewModel.StringProvider() {
            @Override
            public String estimationString() {
                return getString(R.string.estimation);
            }

            @Override
            public String impossibleString() {
                return getString(R.string.impossible);
            }

            @Override
            public String possibleString() {
                return getString(R.string.possible);
            }
        }));
    }
}
