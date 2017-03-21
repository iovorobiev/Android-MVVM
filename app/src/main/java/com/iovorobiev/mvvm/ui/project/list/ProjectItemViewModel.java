package com.iovorobiev.mvvm.ui.project.list;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import com.iovorobiev.mvvm.ui.ProjectModel;

public class ProjectItemViewModel {

    public int id;

    public final String name;

    public final String price;
    public final String speed;
    public final String stability;

    public final ObservableBoolean starred = new ObservableBoolean();

    public ProjectItemViewModel(ProjectModel model,
                                ParameterStringResolver resolver) {
        id = model.id;
        name = model.name;
        price = resolver.getForPrice(model.cheap);
        speed = resolver.getForSpeed(model.fast);
        stability = resolver.getForStability(model.stable);
    }

    public interface ParameterStringResolver {
        String getForPrice(boolean cheap);
        String getForSpeed(boolean fast);
        String getForStability(boolean stable);
    }
}
