package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parentViewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parentViewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO: Complete this method once we have the editor and its fields finalized
    }
}
