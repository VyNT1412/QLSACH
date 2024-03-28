package com.example.qlsach.DTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.GeneratedAdapter;

import com.example.qlsach.General;
import com.example.qlsach.Model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context,
                General.databaseName,
                null,
                1);}

    private static final String TABLE_CREATE_User =
            "CREATE TABLE " + General.TABLE_User + " (" +
                    General.COLUMN_ID_User + " INTEGER PRIMARY KEY AUTOINCREMENT, " + General.COLUMN_Full_Name + " TEXT, " +
                    General.COLUMN_User_Name + " TEXT, " +
                    General.COLUMN_Password + " TEXT, " + General.COLUMN_Email + " TEXT, " + General.COLUMN_SDT + " TEXT, " + General.COLUMN_Role_User + " INTEGER, " + General.COLUMN_GT + " INTEGER, " + General.COLUMN_Date_Of_Birth + " TEXT, " + General.COLUMN_Address + " TEXT);";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_User);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + General.TABLE_User);
        onCreate(db);
    }
    public long addUser(User nv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(General.COLUMN_ID_User, nv.getUser_id());
        values.put(General.COLUMN_Full_Name, nv.getFull_name());
        values.put(General.COLUMN_User_Name, nv.getUser_name());
        values.put(General.COLUMN_Password, nv.getPassword());
        values.put(General.COLUMN_Email, nv.getEmail());
        values.put(General.COLUMN_SDT, nv.getPhone_number());
        values.put(General.COLUMN_GT, nv.getGt());
        values.put(General.COLUMN_Date_Of_Birth, nv.getDate_of_birth());
        values.put(General.COLUMN_Address, nv.getAddress());
        values.put(General.COLUMN_Role_User, nv.getRole_id());

        // Trả về ID của người dùng mới được thêm
        return db.insert(General.TABLE_User, null, values);
    }

    // Phương thức để kiểm tra đăng nhập
    public boolean checkUserKH(String username, String password) {
        int count = 0;  // Initialize count outside of if-else blocks
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {General.COLUMN_User_Name};
        String selection = General.COLUMN_User_Name + "=? AND " + General.COLUMN_Password + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(General.TABLE_User, columns, selection, selectionArgs, null, null, null);
        count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;

    }
    public boolean checkUsername_Phone_Email(String username, String phone, String email) {
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();

            // Chú ý khoảng trắng sau "OR"
            String selection = General.COLUMN_User_Name + "=? OR " + General.COLUMN_SDT + "=? OR " + General.COLUMN_Email + "=?";
            String[] selectionArgs = { username, phone, email };

            cursor = db.query(General.TABLE_User, null, selection, selectionArgs, null, null, null);

            count = cursor.getCount();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }

        return count > 0;
    }
    public int countUser() {
        int count = 0;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM "+ General.TABLE_User, null);
        cursor.moveToFirst();
        count = cursor.getInt(0);
        cursor.close();
        return count;
    }
    public int getLastUserId() {
        SQLiteDatabase db = this.getReadableDatabase();
        int lastUserId = -1;

        Cursor cursor = db.rawQuery("SELECT " + General.COLUMN_ID_User + " FROM " + General.TABLE_User + " ORDER BY " + General.COLUMN_ID_User + " DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            lastUserId = cursor.getInt(0);
            cursor.close();
        }

        db.close();
        return lastUserId;
    }
}
