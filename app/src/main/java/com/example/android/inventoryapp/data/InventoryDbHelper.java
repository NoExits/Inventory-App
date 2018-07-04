package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.inventoryapp.data.InventoryContract.ProductsEntry;

public class InventoryDbHelper extends SQLiteOpenHelper {

    // Create the LOG_TAG for debugging purposes
    private static final String LOG_TAG = InventoryDbHelper.class.getSimpleName();

    // Constant int to hold the version number of the database.
    private static final int DATABASE_VERSION = 1;

    // Constant String to hold the name of our database
    private static final String DATABASE_NAME = "inventory.db";

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //The following SQL statement will create the database
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductsEntry.TABLE_NAME + " ("
                + ProductsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductsEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductsEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
                + ProductsEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL  DEFAULT 0, "
                + ProductsEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " TEXT, "
                + ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE + " TEXT);";

        //Log the SQL statement for debugging purposes
        Log.v(LOG_TAG, "The following statement created the SQL table: " + SQL_CREATE_PRODUCTS_TABLE);

        //Execute this SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Create the string that contains the SQL statement to drop the products table
        String SQL_DROP_
    }
}
