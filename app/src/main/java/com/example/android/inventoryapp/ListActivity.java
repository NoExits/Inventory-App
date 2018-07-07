package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Log tag for debugging purposes
    private static final String LOG_TAG = ListActivity.class.getSimpleName();

    // Global variable for the CursorAdapter
    private ProductCursorAdapter mCursorAdapter;

    // Identification for the loader
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Set up the Floating Action Button
        setupFab();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    // Helper method to set up the Floating Action Button in this activity
    private void setupFab(){
        FloatingActionButton fab = findViewById(R.id.floating_action_button);

        // Set an onClickListener on the FAB which starts the Editor in insert
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
