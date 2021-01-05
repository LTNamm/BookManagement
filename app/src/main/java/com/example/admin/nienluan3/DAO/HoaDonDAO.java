package com.example.admin.nienluan3.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.nienluan3.Model.HoaDon;
import com.example.admin.nienluan3.SQLite.Dbhelper;

import java.util.ArrayList;

public class HoaDonDAO {
    Dbhelper dbh;
    SQLiteDatabase db;

    public HoaDonDAO(Context context) {
        dbh = new Dbhelper(context);
    }

    public void insertHoaDon(HoaDon hoaDon) {
        db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("maHoadon", hoaDon.maHoadon);
        values.put("ngayMua", hoaDon.ngayMua);
        db.insert("hoaDon", null, values);
    }

    public ArrayList<HoaDon> viewHoaDon(){
        return getHoaDon("select * from hoaDon");
    }
    public ArrayList<HoaDon> getHoaDon(String sql, String... selectionArgs) {
        db = dbh.getReadableDatabase();

        ArrayList<HoaDon> listHoaDon = new ArrayList<HoaDon>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                String maHoaDon = cursor.getString(0);
                String ngayMua = cursor.getString(1);
                HoaDon hoaDon = new HoaDon(maHoaDon, ngayMua);
                listHoaDon.add(hoaDon);
            } while (cursor.moveToNext());
        }
        return listHoaDon;
    }

    public void deleteHoaDon(String maHoaDon) {
        db = dbh.getWritableDatabase();
        db.delete("hoaDon", "maHoaDon=?", new String[]{maHoaDon});
    }
}
