package com.example.admin.nienluan3.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.nienluan3.Model.Sach;
import com.example.admin.nienluan3.SQLite.Dbhelper;

import java.util.ArrayList;

public class SachDAO {
    Dbhelper dbh;
    SQLiteDatabase db;

    public SachDAO(Context context) {
        dbh = new Dbhelper(context);
    }

    public void insertSach(Sach sach) {
        db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSach", sach.maSach);
        values.put("maTheLoai", sach.maTheLoai);
        values.put("tenSach", sach.tenSach);
        values.put("tacGia", sach.tacGia);
        values.put("NXB", sach.NXB);
        values.put("giaBia", sach.giaBia);
        values.put("soLuong", sach.soLuong);
        db.insert("Sach", null, values);
    }

    public ArrayList<Sach> viewSach() {
        return getSach("Select * from Sach");
    }

    public ArrayList<Sach> getSach(String sql, String... selectionArgs) {

        db = dbh.getReadableDatabase();

        ArrayList<Sach> listSach = new ArrayList<Sach>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                String maSach = cursor.getString(0);
                String maTheLoai = cursor.getString(1);
                String tenSach = cursor.getString(2);
                String tacGia = cursor.getString(3);
                String NXB = cursor.getString(4);
                double giaBia = cursor.getDouble(5);
                int soLuong = cursor.getInt(6);
                Sach sach = new Sach(maSach, maTheLoai, tenSach, tacGia, NXB, giaBia, soLuong);
                listSach.add(sach);
            } while (cursor.moveToNext());
        }
        return listSach;
    }


    public void deleteSach(String maSach) {
        db = dbh.getWritableDatabase();

        db.delete("Sach", "maSach=?", new String[]{maSach});
    }

    public void updateSach(Sach sach) {
        db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maSach", sach.maSach);
        values.put("maTheLoai", sach.maTheLoai);
        values.put("tenSach", sach.tenSach);
        values.put("tacGia", sach.tacGia);
        values.put("NXB", sach.NXB);
        values.put("giaBia", sach.giaBia);
        values.put("soLuong", sach.soLuong);
        db.update("Sach", values, "maSach=?", new String[]{sach.maSach});
    }
}
