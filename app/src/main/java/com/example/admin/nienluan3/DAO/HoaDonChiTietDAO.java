package com.example.admin.nienluan3.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.nienluan3.Model.HoaDonChiTiet;
import com.example.admin.nienluan3.SQLite.Dbhelper;

import java.util.ArrayList;

public class HoaDonChiTietDAO {
    Dbhelper dbh;
    SQLiteDatabase db;

    public HoaDonChiTietDAO(Context context) {
        dbh = new Dbhelper(context);
    }

    public void insertHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDonChiTiet.maHoaDon);
        values.put("maSach", hoaDonChiTiet.maSach);
        values.put("soLuongMua", hoaDonChiTiet.soLuongMua);
        values.put("thanhTien", hoaDonChiTiet.thanhTien);
        db.insert("hoaDonChiTiet", null, values);
    }

    public ArrayList<HoaDonChiTiet> viewHoaDonChiTiet() {
        return getHoaDonChiTiet("select * from hoaDonChiTiet");
    }



    public ArrayList<HoaDonChiTiet> getHoaDonChiTiet(String sql, String... selectionArgs) {
        db = dbh.getReadableDatabase();

        ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<HoaDonChiTiet>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int maHoaDonChiTiet = cursor.getInt(0);
                String maHoaDon = cursor.getString(1);
                String maSach = cursor.getString(2);
                int soLuongMua = cursor.getInt(3);
                String thanhTien = cursor.getString(4);
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(maHoaDonChiTiet, maHoaDon, maSach, soLuongMua, thanhTien);
                listHoaDonChiTiet.add(hoaDonChiTiet);
            } while (cursor.moveToNext());
        }
        return listHoaDonChiTiet;
    }

    public void deleteHoaDonChiTiet(int maHoaDonChiTiet) {
        db = dbh.getWritableDatabase();
        db.delete("hoaDonChiTiet", "maHoaDonChiTiet=?", new String[]{String.valueOf(maHoaDonChiTiet)});
    }

    public void UpdateHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDonChiTiet.maHoaDon);
        values.put("soLuongMua", hoaDonChiTiet.soLuongMua);
        values.put("thanhTien", hoaDonChiTiet.thanhTien);
        db.update("hoaDonChiTiet", values, "maSach=?", new String[]{hoaDonChiTiet.maSach});
    }
}
