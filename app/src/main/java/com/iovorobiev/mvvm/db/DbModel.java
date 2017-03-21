package com.iovorobiev.mvvm.db;

import android.database.Cursor;

public interface DbModel<T> {
    void save();
    T buildFromCursor(Cursor cursor);
}
