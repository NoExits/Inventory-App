package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    // Content authority for the content providers
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";

    // Base URI for content providers
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // URI addition for the products table
    public static final String PATH_PRODUCTS = "products";

    public static abstract class ProductsEntry implements BaseColumns {

        // Name of the table
        public static final String TABLE_NAME = "products";

        // Content URI for this table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH_PRODUCTS);

        // MIME types for a single product and a list of products
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        // Column names for the products table
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_PRODUCT_SUPPLIER_PHONE = "supplier_phone";
    }
}
