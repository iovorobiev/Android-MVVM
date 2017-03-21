package com.iovorobiev.mvvm.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.iovorobiev.mvvm.core.App;
import com.iovorobiev.mvvm.ui.DbProjectModel;
import com.iovorobiev.mvvm.ui.ProjectModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public class DbApi {
    private ThreadPoolExecutor executor;
    private ConcurrentHashMap<UUID, FutureTask<UUID>> tasks = new ConcurrentHashMap<>();

    public DbApi() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public UUID loadProjects(@NonNull final OnDataLoadedListener<ProjectModel> listener) {
        final UUID taskId = UUID.randomUUID();
        FutureTask<UUID> futureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {
                List<ProjectModel> result = loadAll();
                listener.dataLoaded(result);
                tasks.remove(taskId);
                Log.d("Executor", "Current tasks number: " + executor.getActiveCount());
            }
        }, taskId);
        tasks.put(taskId, futureTask);
        executor.submit(futureTask);
        return taskId;
    }

    public UUID loadProject(final int id, @NonNull final OnObjectLoadedListener<ProjectModel> listener) {
        final UUID taskId = UUID.randomUUID();
        FutureTask<UUID> futureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {
                ProjectModel result = loadProjectById(id);
                listener.objectLoaded(result);
                tasks.remove(taskId);
                Log.d("Executor", "Current tasks number: " + executor.getActiveCount());
            }
        }, taskId);
        tasks.put(taskId, futureTask);
        executor.submit(futureTask);
        return taskId;
    }

    public UUID saveModel(final String tableName, final ContentValues values, @Nullable final OperationNotifier listener) {
        final UUID taskId = UUID.randomUUID();
        FutureTask<UUID> futureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {
                save(tableName, values);
                if (listener != null) {
                    listener.success();
                }
                tasks.remove(taskId);
            }
        }, taskId);
        tasks.put(taskId, futureTask);
        executor.submit(futureTask);
        return taskId;
    }

    private void save(String tableName, ContentValues values) {
        SQLiteDatabase db = App.db.getWritableDatabase();
        db.insert(tableName, null, values);
    }

    List<ProjectModel> loadAll() {
        List<ProjectModel> result = new ArrayList<>();
        SQLiteDatabase db = App.db.getWritableDatabase();
        Cursor cursor = db.query(DbProjectModel.TABLE_NAME,
                new String[] {
                        DbProjectModel.FIELD_ID,
                        DbProjectModel.FIELD_NAME,
                        DbProjectModel.FIELD_STABLE,
                        DbProjectModel.FIELD_FAST,
                        DbProjectModel.FIELD_CHEAP},
                null, null,
                null, null,
                DbProjectModel.FIELD_ID + " DESC");

        DbProjectModel dbModel = new DbProjectModel();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            result.add(dbModel.buildFromCursor(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return result;
    }

    ProjectModel loadProjectById(int id) {
        SQLiteDatabase db = App.db.getWritableDatabase();
        Cursor cursor = db.query(DbProjectModel.TABLE_NAME,
                new String[] {
                        DbProjectModel.FIELD_ID,
                        DbProjectModel.FIELD_NAME,
                        DbProjectModel.FIELD_STABLE,
                        DbProjectModel.FIELD_FAST,
                        DbProjectModel.FIELD_CHEAP},
                DbProjectModel.FIELD_ID + " = ?", new String[]{Integer.toString(id)},
                null, null,
                null);

        DbProjectModel dbModel = new DbProjectModel();
        cursor.moveToFirst();
        ProjectModel result = dbModel.buildFromCursor(cursor);
        cursor.close();
        return result;
    }

    public void unsubscribeTaskWithId(UUID id) {
        if (tasks.containsKey(id)) {
            FutureTask<UUID> task = tasks.get(id);
            if (task.isDone() || task.isCancelled()) {
                return;
            }
            task.cancel(true);
        }
    }

    public interface OnDataLoadedListener<T> {
        void dataLoaded(List<T> data);
    }

    public interface OnObjectLoadedListener<T> {
        void objectLoaded(T data);
    }

    public interface OperationNotifier {
        void success();
        void error(Throwable e);
    }
}
