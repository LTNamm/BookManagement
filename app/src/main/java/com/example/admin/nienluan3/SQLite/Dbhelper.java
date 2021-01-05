package com.example.admin.nienluan3.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DatabaseBookManager";
    public static final int DB_VERSION = 1;

    public Dbhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String nguoiDung = "create table nguoiDung" +
                "( userName text primary key, " +
                "passWord text, " +
                "phone text, " +
                "hoVaTen text" +
                ")";
        db.execSQL(nguoiDung);                  //execSQL là lệnh dùng để thực hiện truy vấn ko trả kết quả

        String TheLoai = "create table theLoai" +
                "( " +
                "maTheLoai text primary key, " +
                "tenTheLoai text, " +
                "moTa text, " +
                "viTri integer" +
                ")";
        db.execSQL(TheLoai);

        String Sach = "create table Sach" +
                "( " +
                "maSach text primary key, " +
                "maTheLoai text, " +
                "tenSach text, " +
                "tacGia text, " +
                "NXB text, " +
                "giaBia float, " +
                "soLuong integer" +
                ")";
        db.execSQL(Sach);

        String hoaDon = "create table hoaDon" +
                "( " +
                "maHoaDon text primary key, " +
                "ngayMua text" +
                ")";
        db.execSQL(hoaDon);

        String hoaDonChiTiet = "create table hoaDonChiTiet" +
                "( " +
                "maHoaDonChiTiet integer primary key autoincrement, " +
                "maHoaDon text, " +
                "maSach text, " +
                "soLuongMua integer," +
                "thanhTien text" +
                ")";
        db.execSQL(hoaDonChiTiet);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
            Nếu đã có cái bảng thì ko tạo nữa
         */
        db.execSQL("drop table if exists nguoiDung");
        db.execSQL("drop table if exists theLoai");
        db.execSQL("drop table if exists Sach");
        db.execSQL("drop table if exists hoaDon");
        db.execSQL("drop table if exists hoaDonChiTiet");
    }
}
