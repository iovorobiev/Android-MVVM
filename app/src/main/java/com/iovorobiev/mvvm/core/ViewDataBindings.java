package com.iovorobiev.mvvm.core;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.TextView;

public class ViewDataBindings {
    @BindingAdapter("drawableTint")
    public void setDrawableTint(TextView view, @ColorInt int color) {
        Drawable[] compounds = view.getCompoundDrawables();
        Drawable[] wrappedCompounds = new Drawable[compounds.length];
        for (int i = 0; i < compounds.length; i++) {
            if (compounds[i] == null) {
                continue;
            }
            Drawable wrappedCompound = DrawableCompat.wrap(compounds[i]).mutate();
            DrawableCompat.setTint(wrappedCompound, color);
            wrappedCompounds[i] = wrappedCompound;
        }
        view.setCompoundDrawables(wrappedCompounds[0], wrappedCompounds[1],
                wrappedCompounds[2], wrappedCompounds[3]);
        view.invalidate();
    }
}
