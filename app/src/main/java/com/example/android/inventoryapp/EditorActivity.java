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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Public and private global variables to get the intended mode of the activity. Either insert or edit
    // variables must be sent along with the activity's starter intent to figure out which 'mode'
    // the activity is in currently.
    private int activityMode;
    public static final int ACTIVITY_MODE_INSERT = 0;
    public static final int ACTIVITY_MODE_EDIT = 1;

    // Global variable for the Uri passed along with the starter intent of this activity
    // Note if we are inserting a new product, no value will be assigned to this variable.
    private Uri mExistingProductUri;

    // Identification for the Loader
    private static final int LOADER_ID = 2;

    // Global variables for the EditText fields we are using
    EditText mProductName;
    EditText mProductPrice;
    EditText mProductQuantity;
    EditText mSupplierName;
    EditText mSupplierPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find reference to all the relevant EditText views
        mProductName = findViewById(R.id.editor_field_product_name);
        mProductPrice = findViewById(R.id.editor_field_product_price);
        mProductQuantity = findViewById(R.id.editor_field_product_quantity);
        mSupplierName = findViewById(R.id.editor_field_supplier_name);
        mSupplierPhone = findViewById(R.id.editor_field_supplier_phone);

        // Handle the intent that started this activity and set the URI (if it exists)
        // and the title accordingly. Additionally, initialize the CursorLoader
        handleStarterIntent(getIntent());
    }

    // Helper method to handle the intent that started this activity and set it to either
    // edit or insert modes.
    private void handleStarterIntent(Intent starterIntent) {
        activityMode = starterIntent.getIntExtra("activityMode", 0);

        switch (activityMode) {
            case ACTIVITY_MODE_INSERT:
                setTitle(starterIntent.getIntExtra
                        ("activityTitle", R.string.activity_title_insert));
                invalidateOptionsMenu();
                break;

            case ACTIVITY_MODE_EDIT:
                setTitle(starterIntent.getIntExtra
                        ("activityTitle", R.string.activity_title_edit));
                mExistingProductUri = starterIntent.getData();
                // In this mode we need to load data so we'll initiate a Loader
                getLoaderManager().initLoader(LOADER_ID, null, this);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the overflow and action bar menu from res/menu/menu_editor.xml
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // // Handle the user click in the overflow menu
        // TODO: Finish the overflow menu items
        switch (item.getItemId()) {
            case R.id.editor_action_save:
                saveProduct();
            case R.id.editor_action_delete_single_product:
                // Do stuff
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (activityMode == ACTIVITY_MODE_INSERT) {
            MenuItem item = menu.findItem(R.id.editor_action_delete_single_product);
            item.setVisible(false);
        }
        return true;
    }

    private void saveProduct() {
        // Get the data (all in strings for now) in the editor fields
        String productName = mProductName.getText().toString().trim();
        String productPrice = mProductPrice.getText().toString().trim();
        String productQuantity = mProductQuantity.getText().toString().trim();
        String supplierName = mSupplierName.getText().toString().trim();
        String supplierPhone = mSupplierPhone.getText().toString().trim();

        long productPriceLong = Long.valueOf(productPrice);
        int productQuantityInt = Integer.valueOf(productQuantity);

        // Put these values into a ContentValues object
        ContentValues values = new ContentValues();
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_NAME, productName);
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_PRICE, productPriceLong);
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_QUANTITY, productQuantityInt);
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, supplierPhone);

        // Depending on the current mode of the activity, either insert or update a product
        switch (activityMode) {
            case ACTIVITY_MODE_INSERT:
                Uri returnedUri = getContentResolver().insert
                        (InventoryContract.ProductsEntry.CONTENT_URI, values);

                if (returnedUri == null) {
                    Toast.makeText(this, R.string.editor_hint_db_insertion_unsuccessful,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.editor_hint_db_insertion_successful,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case ACTIVITY_MODE_EDIT:
                int rowsAffected = getContentResolver().update
                        (mExistingProductUri, values, null, null);

                if (rowsAffected == 0) {
                    Toast.makeText(this, R.string.editor_hint_db_update_successful,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.editor_hint_db_update_unsuccessful,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection
        String[] projection = {
                InventoryContract.ProductsEntry._ID,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_PRICE,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE
        };

        // Set the selection and selection arguments so that we are only querying for a single product
        String selection = InventoryContract.ProductsEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(ContentUris.parseId(mExistingProductUri))};

        // Return the CursorLoader to the onLoadFinished method
        return new CursorLoader(
                this,
                InventoryContract.ProductsEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Move the cursor to the first index so we avoid CursorOutOfBounds exceptions
        cursor.moveToFirst();

        if (cursor.getCount() < 1) {
            return;
        }
        // Get the data we are aiming to populate
        String productNameString = cursor.getString
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_NAME));
        long productPriceLong = cursor.getLong
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_PRICE));
        int productQuantityInt = cursor.getInt
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_QUANTITY));
        String supplierNameString = cursor.getString
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_NAME));
        String supplierPhoneString = cursor.getString
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE));

        // Populate the fields with the data
        mProductName.setText(productNameString);
        mProductPrice.setText(String.valueOf(productPriceLong));
        mProductQuantity.setText(String.valueOf(productQuantityInt));
        mSupplierName.setText(supplierNameString);
        mSupplierPhone.setText(supplierPhoneString);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Reset the values of each field in the editor
        mProductName.setText("");
        mProductPrice.setText("");
        mProductQuantity.setText("");
        mSupplierName.setText("");
        mSupplierPhone.setText("");
    }
}
