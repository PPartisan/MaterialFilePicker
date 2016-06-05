package com.nbsp.materialfilepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.nbsp.materialfilepicker.filter.CompositeFilter;
import com.nbsp.materialfilepicker.filter.HiddenFilter;
import com.nbsp.materialfilepicker.filter.PatternFilter;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Dimorinny on 25.02.16.
 */
public class MaterialFilePicker {
    private Activity mActivity;
    private Integer mRequestCode;
    private Pattern mFileFilter;
    private Boolean mDirectoriesFilter = false;
    private String mRootPath;
    private String mCurrentPath;
    private Boolean mShowHidden = false;

    private int color = -1;

    public MaterialFilePicker(@NonNull Activity activity, @NonNull Integer requestCode) {
        mActivity = activity;
        mRequestCode = requestCode;
    }

    public MaterialFilePicker withFilter(Pattern pattern) {
        mFileFilter = pattern;
        return this;
    }

    public MaterialFilePicker withFilterDirectories(boolean directoriesFilter) {
        mDirectoriesFilter = directoriesFilter;
        return this;
    }

    public MaterialFilePicker withRootPath(String rootPath) {
        mRootPath = rootPath;
        return this;
    }

    public MaterialFilePicker withPath(String path) {
        mCurrentPath = path;
        return this;
    }

    public MaterialFilePicker withHiddenFiles(boolean show) {
        mShowHidden = show;
        return this;
    }

    public MaterialFilePicker withFileColor(int color) {
        this.color = color;
        return this;
    }

    private CompositeFilter getFilter() {
        ArrayList<FileFilter> filters = new ArrayList<>();

        if (!mShowHidden) {
            filters.add(new HiddenFilter());
        }

        if (mFileFilter != null) {
            filters.add(new PatternFilter(mFileFilter, mDirectoriesFilter));
        }

        return new CompositeFilter(filters);

    }

    public void start() {

        CompositeFilter filter = getFilter();

        Intent intent = new Intent(mActivity, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.ARG_FILTER, filter);

        if (mRootPath != null) {
            intent.putExtra(FilePickerActivity.ARG_START_PATH, mRootPath);
        }

        if (mCurrentPath != null) {
            intent.putExtra(FilePickerActivity.ARG_CURRENT_PATH, mCurrentPath);
        }

        intent.putExtra(FilePickerActivity.ARG_FILE_COLOR, color);

        mActivity.startActivityForResult(intent, mRequestCode);
    }
}
