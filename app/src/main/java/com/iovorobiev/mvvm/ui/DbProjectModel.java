package com.iovorobiev.mvvm.ui;
import android.content.ContentValues;
import android.database.Cursor;
import com.iovorobiev.mvvm.db.DbApi;
import com.iovorobiev.mvvm.db.DbModel;

public class DbProjectModel implements DbModel<ProjectModel> {
    DbApi db = new DbApi();
    ProjectModel model;

    public DbProjectModel() { }

    public DbProjectModel(ProjectModel model) {
        this.model = model;
    }

    @Override
    public void save() {
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, model.name);
        values.put(FIELD_CHEAP, model.cheap ? 1 : 0);
        values.put(FIELD_FAST, model.fast ? 1 : 0);
        values.put(FIELD_STABLE, model.stable ? 1 : 0);
        db.saveModel(TABLE_NAME, values, null);
    }

    @Override
    public ProjectModel buildFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
        String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
        boolean cheap = cursor.getInt(cursor.getColumnIndex(FIELD_CHEAP)) == 1;
        boolean fast = cursor.getInt(cursor.getColumnIndex(FIELD_FAST)) == 1;
        boolean stable = cursor.getInt(cursor.getColumnIndex(FIELD_STABLE)) == 1;
        model = new ProjectModel(name, cheap, fast, stable);
        model.id = id;
        return model;
    }

    public static String getCreateRequest() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY," +
                FIELD_NAME + " TEXT," +
                FIELD_CHEAP + " INTEGER," +
                FIELD_FAST + " INTEGER," +
                FIELD_STABLE + " INTEGER" +
                " )";
    }

    public static final String TABLE_NAME = "dev_project";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CHEAP = "cheap";
    public static final String FIELD_FAST = "fast";
    public static final String FIELD_STABLE = "stable";
}
