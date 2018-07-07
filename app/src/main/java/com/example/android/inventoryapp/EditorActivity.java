package com.example.android.inventoryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EditorActivity extends AppCompatActivity {

    // Local and global variables to get the intended mode of the activity. Either insert or edit
    // variables must be sent along with the activity's starter intent to figure out which 'mode'
    // the activity is in currently.
    private int activity_mode;
    public static final int ACTIVITY_MODE_INSERT = 0;
    public static final int ACTIVITY_MODE_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
    }
}
