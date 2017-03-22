package com.iovorobiev.mvvm.ui.project.create;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import com.iovorobiev.mvvm.core.INavigation;
import com.iovorobiev.mvvm.ui.DbProjectModel;
import com.iovorobiev.mvvm.ui.ProjectModel;

public class CreateProjectViewModel {

    private final INavigation navigation;
    private final StringProvider provider;

    public final ObservableBoolean cheap = new ObservableBoolean();
    public final ObservableBoolean fast = new ObservableBoolean();
    public final ObservableBoolean stable = new ObservableBoolean();

    public final ObservableBoolean isPossible = new ObservableBoolean(true);
    public final ObservableField<String> description = new ObservableField<>();

    private final Observable.OnPropertyChangedCallback possibilityUpdateCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            isPossible.set(isPossible());
            updateDescription();
        }
    };

    public ObservableField<String> name = new ObservableField<>();

    public CreateProjectViewModel(INavigation navigation, StringProvider provider) {
        this.navigation = navigation;
        this.provider = provider;
        updateDescription();
        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateDescription();
            }
        });
        cheap.addOnPropertyChangedCallback(possibilityUpdateCallback);
        fast.addOnPropertyChangedCallback(possibilityUpdateCallback);
        stable.addOnPropertyChangedCallback(possibilityUpdateCallback);
    }

    private void updateDescription() {
        description.set(String.format(provider.estimationString(),
                name.get(),
                isPossible() ? provider.possibleString() : provider.impossibleString())
        );
    }

    private boolean isPossible() {
        return !(cheap.get() && fast.get() && stable.get());
    }

    public void create() {
        ProjectModel model = new ProjectModel(
                name.get(),
                cheap.get(),
                fast.get(),
                stable.get()
        );
        new DbProjectModel(model).save();
        navigation.openSoftwareList();
    }

    public interface StringProvider {
        String estimationString();
        String impossibleString();
        String possibleString();
    }
}
