package com.example.admin.nienluan3.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.nienluan3.Model.TheLoai;
import com.example.admin.nienluan3.SQLite.Dbhelper;

import java.util.ArrayList;

public class TheLoaiDAO {
    Dbhelper dbh;
    SQLiteDatabase db;

    public TheLoaiDAO(Context context) {
        dbh = new Dbhelper(context);
    }

    public void insertTheLoai(TheLoai theLoai) {
        db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maTheLoai", theLoai.maTheLoai);
        values.put("tenTheLoai", theLoai.tenTheLoai);
        values.put("moTa", theLoai.moTa);
        values.put("viTri", theLoai.viTri);

        db.insert("theLoai", null, values);
    }

    public ArrayList<TheLoai> viewTheLoai(){
        return getTheLoai("Select * from theLoai");
    }

    public ArrayList<TheLoai> getTheLoai(String sql, String... selectionArgs) {
        db = dbh.getReadableDatabase();
        ArrayList<TheLoai> listTheLoai = new ArrayList<TheLoai>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                String maTheLoai = cursor.getString(0);
                String tenTheLoai = cursor.getString(1);
                String moTa = cursor.getString(2);
                int viTri = cursor.getInt(3);
                TheLoai theLoai = new TheLoai(maTheLoai, tenTheLoai, moTa, viTri);
                listTheLoai.add(theLoai);
            } while (cursor.moveToNext());
        }
        return listTheLoai;
    }

    public void deleteTheLoai(String maTheLoai) {
        db = dbh.getWritableDatabase();
        db.delete("theLoai", "maTheLoai=?", new String[]{maTheLoai});
    }

    public void updateTheLoai(TheLoai theLoai) {

        db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTheLoai", theLoai.maTheLoai);
        values.put("tenTheLoai", theLoai.tenTheLoai);
        values.put("moTa", theLoai.moTa);
        values.put("viTri", theLoai.viTri);
        db.update("theLoai", values, "maTheLoai=?", new String[]{theLoai.maTheLoai});

    }
}
