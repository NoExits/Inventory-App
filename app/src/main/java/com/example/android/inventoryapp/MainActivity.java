package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.inventoryapp.data.InventoryContract.ProductsEntry;
import com.example.android.inventoryapp.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity {

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insertData() {
        //Get an instance of the InventoryDbHelper class
        mDbHelper = new InventoryDbHelper(this);

        //Get an instance of the writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Put our dummy data into an instance of ContentValues
        ContentValues values = new ContentValues();
        values.put(ProductsEntry.COLUMN_PRODUCT_NAME, "Copypaper, A4, 80 g, VICTORIA");
        values.put(ProductsEntry.COLUMN_PRODUCT_PRICE, 0.5);
        values.put(ProductsEntry.COLUMN_PRODUCT_QUANTITY, 5);
        values.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "Jane Doe");
        values.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, "+36 70 123 4567");

        //Put these values into a new column of the table
        long newRowId = db.insert(ProductsEntry.TABLE_NAME, null, values);
    }

    public void queryData() {
        //Get an instance of the InventoryDbHelper class
        mDbHelper = new InventoryDbHelper(this);

        //Get an instance of the readable database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Define the projection (aka.: what columns do we want returned)
        String[] projection = {
                ProductsEntry._ID,
                ProductsEntry.COLUMN_PRODUCT_NAME,
                ProductsEntry.COLUMN_PRODUCT_PRICE,
                ProductsEntry.COLUMN_PRODUCT_QUANTITY,
        };

        //Since we don't want any filtering or ordering we can just define the cursor as is
        Cursor cursor = db.query(
                ProductsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );

        //Do stuff with the cursor data
        //This will be completed at a later stage of the project

        //Close the cursor
        cursor.close();
    }
}
