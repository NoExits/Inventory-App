package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

public final class InventoryContract {

    public static abstract class ProductsEntry implements BaseColumns {

        /**
         * Name of the table
         */
        public static final String TABLE_NAME = "products";

        /**
         * Name of the columns inside the table
         */
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_PRODUCT_SUPPLIER_PHONE = "supplier_phone";
    }
}
