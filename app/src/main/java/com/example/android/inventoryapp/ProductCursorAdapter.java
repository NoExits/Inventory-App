package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parentViewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parentViewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find reference to the views and buttons we'll use
        TextView productNameView = view.findViewById(R.id.list_item_product_name);
        TextView productPriceView = view.findViewById(R.id.list_item_product_price);
        TextView productQuantityView = view.findViewById(R.id.list_item_product_quantity);
        Button sellButton = view.findViewById(R.id.list_item_sell_product);

        // Get the data from the cursor
        String productName = cursor.getString
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_NAME));
        String productPrice = cursor.getString
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_PRICE));
        String productQuantity = cursor.getString
                (cursor.getColumnIndex(InventoryContract.ProductsEntry.COLUMN_PRODUCT_QUANTITY));

        // Add the extra strings to the price and quantity variables
        productPrice = context.getString(R.string.list_item_hint_unit_price) + " " + productPrice;
        String productQuantityString = context.getString(R.string.list_item_hint_units_left) + " " + productQuantity;

        // Populate the views with the data
        productNameView.setText(productName);
        productPriceView.setText(productPrice);
        productQuantityView.setText(productQuantityString);

        // Decrease the number of the remaining quantity by one upon clicking on the Sell button
        final int currentQuantity = Integer.valueOf(productQuantity);
        final int currentProductId = cursor.getInt(cursor.getColumnIndex(InventoryContract.ProductsEntry._ID));
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuantity > 0) {
                    int newQuantity = currentQuantity - 1;
                    Uri uri = ContentUris.withAppendedId(InventoryContract.ProductsEntry.CONTENT_URI, currentProductId);
                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.ProductsEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
                    context.getContentResolver().update(uri, values, null, null);
                }
            }
        });
    }
}