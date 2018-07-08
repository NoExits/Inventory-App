package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

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

        // Find reference to the ListView and the EmptyTextView
        ListView listView = findViewById(R.id.list_view);
        View emptyTextView = findViewById(R.id.list_activity_empty_view);

        // Initialize the CursorAdapter. We don't have a cursor yet so we'll pass in null.
        mCursorAdapter = new ProductCursorAdapter(this, null);

        // Attach the CursorAdapter to the listView and add the emptyTextView as well.
        listView.setAdapter(mCursorAdapter);
        listView.setEmptyView(emptyTextView);

        // Set an onItemClickListener on the listView which creates an intent that sends the user
        // to the EditorActivity in edit mode. This means we'll need to add the necessary extras.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent editProductIntent = new Intent(ListActivity.this, EditorActivity.class);
                editProductIntent.putExtra("activityTitle", R.string.activity_title_edit);
                editProductIntent.putExtra("activityMode", EditorActivity.ACTIVITY_MODE_EDIT);

                // Find the URI we'll need and pass it along.
                Uri uri = ContentUris.withAppendedId(InventoryContract.ProductsEntry.CONTENT_URI, id);
                editProductIntent.setData(uri);

                startActivity(editProductIntent);
            }
        });

        // Initialize the Loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    // Override the framework method that creates the action bar menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the overflow menu from res/menu/menu_list.xml
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    // Override the framework method that handles the user clicks in the action bar menus
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the user click in the overflow menu
        // TODO: Finish the overflow menu items
        switch (item.getItemId()) {
            case R.id.list_activity_delete_all_products:
                // Do stuff...
                return true;
            case R.id.list_activity_insert_single_product:
                insertTestProduct();
                return true;
            case R.id.list_activity_insert_multiple_products:
                // Do stuff...
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        // Define our projection
        String[] projection = {
                InventoryContract.ProductsEntry._ID,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_PRICE
        };

        // This loader will execute the ContentProviders
        // query method on a background thread
        return new CursorLoader(this,
                InventoryContract.ProductsEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Swap the cursor with the loaded data
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Clear the cursor if we are resetting the Loader
        mCursorAdapter.swapCursor(null);
    }

    // Helper method to set up the Floating Action Button in this activity
    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.floating_action_button);

        // Set an onClickListener on the FAB which starts the Editor in insert mode with the
        // necessary extras.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newProductIntent = new Intent(ListActivity.this, EditorActivity.class);
                newProductIntent.putExtra("activityTitle", R.string.activity_title_insert);
                newProductIntent.putExtra("activityMode", EditorActivity.ACTIVITY_MODE_INSERT);
                startActivity(newProductIntent);
            }
        });
    }

    // Helper method to insert a single dummy product into the database
    private void insertTestProduct() {
        ContentValues values = new ContentValues();

        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_NAME, "Copypaper, A4, 40G");
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_PRICE, 352);
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_QUANTITY, 12);
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "Jane Doe");
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, "+36 70 123 4567");

        Uri returnedUri = getContentResolver().insert(InventoryContract.ProductsEntry.CONTENT_URI, values);

        if (returnedUri == null) {
            Toast.makeText(this, R.string.activity_hint_db_insertion_unsuccessful, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.activity_hint_db_insertion_successful, Toast.LENGTH_SHORT).show();
        }
    }
}