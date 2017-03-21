package com.iovorobiev.mvvm;

import com.iovorobiev.mvvm.ui.project.create.CreateProjectViewModel;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CreateVmTest {
    private static final String POSSIBLE = "possible";
    private static final String IMPOSSIBLE = "impossible";

    @Test
    public void changeTest() throws Exception {
        CreateProjectViewModel vm = new CreateProjectViewModel(
                new TestNavigation(),
                new CreateProjectViewModel.StringProvider() {
                    @Override
                    public String estimationString() {
                        return "%s %s";
                    }

                    @Override
                    public String impossibleString() {
                        return IMPOSSIBLE;
                    }

                    @Override
                    public String possibleString() {
                        return POSSIBLE;
                    }
                }
        );
        vm.name.set("");
        vm.cheap.set(true);
        assertEquals(" " + POSSIBLE, vm.description.get());

        assertEquals(true, vm.isPossible.get());
        vm.fast.set(true);
        assertEquals(true, vm.isPossible.get());
        vm.stable.set(true);
        assertEquals(false, vm.isPossible.get());
        vm.cheap.set(false);
        assertEquals(true, vm.isPossible.get());
    }
}