package com.example.test;

import com.example.test.DatabaseHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.your_layout);

        databaseHelper = new DatabaseHelper(this);

        // Declare db variable only once
        SQLiteDatabase db = null;

        try {
            db = databaseHelper.getWritableDatabase();
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("SELECT 1", null);
                if (cursor != null) {
                    cursor.close();
                    Toast.makeText(MainActivity.this, "Database connected and operational", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Database connected but query failed", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Database connection failed", Toast.LENGTH_SHORT).show();
            Log.e("DatabaseError", "Database connection error", e);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_id), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}