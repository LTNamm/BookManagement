package com.example.admin.nienluan3.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.nienluan3.Model.NguoiDung;
import com.example.admin.nienluan3.SQLite.Dbhelper;

import java.util.ArrayList;

public class NguoiDungDAO {
    SQLiteDatabase db;
    Dbhelper dbh;

    //public: truy cập ĐƯỢC từ bất cứ lớp class nào
    //privite: trong lớp
    //static: hằng số: không thể thay đổi được
    //hàm void ko trả về

    public static String TAG = "NguoiDungDAO";
    public static String TABLE_NAME = "nguoiDung";

    public NguoiDungDAO(Context context) {
        dbh = new Dbhelper(context);
    }

    /*
        Lưu người dùng
     */
    public int insertNguoiDung(NguoiDung nguoiDung) {
        db = dbh.getWritableDatabase();         //kết nối cơ sở dữ liệu

        //Write: dùng để kết nối
        //Read: seclect, ....

        /*
        * Tiến hành lưu dữ liệu ng dùng nhập vào values
        * */

        ContentValues values = new ContentValues();
        /*key - value */
        values.put("userName", nguoiDung.userName);
        values.put("passWord", nguoiDung.passWord);
        values.put("phone", nguoiDung.phone);
        values.put("hoVaTen", nguoiDung.hoVaTen);



        /*
        * insert bằng câu lệnh hỗ trợ sẵn "insert"
        * if thành công trả về giá trị -1
        * */
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.d("ErrInsert", ex.getMessage());
        }
        return 1;
    }

    /*
    * Mảng chứa tối tượng ArrayList<NguoiDung>
    * */
    public ArrayList<NguoiDung> viewNguoiDung() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getNguoiDung(sql);
    }

    /*
    * Hàm dùng để lấy tất cả thông tin ng dùng
    * */
    public ArrayList<NguoiDung> getNguoiDung(String sql, String... selectionArgs) {         //mảng có 2 tham số truyền vào dùng cho

        /*
        * Thực hiện tạo bảng ở lớp Dbhelper
        * */
        db = dbh.getReadableDatabase();                 //cấu trúc

        ArrayList<NguoiDung> listNguoiDung = new ArrayList <NguoiDung>();

        Cursor cursor = db.rawQuery(sql, selectionArgs);                                    //sql, selectionArgs

        if (cursor.moveToFirst()) {
            do {
                String userName = cursor.getString(0);                              //từ con trỏ lấy thông tin của  cột thứ 0 trong bảng và gán vào userName
                String passWord = cursor.getString(1);
                String phone = cursor.getString(2);
                String hoVaTen = cursor.getString(3);

                /*Tạo một đối tượng nguoiDung mới*/
                NguoiDung nguoiDung = new NguoiDung(userName, passWord, phone, hoVaTen);

                listNguoiDung.add(nguoiDung);

            } while (cursor.moveToNext());                      //duy chuyển qua các cột của hàng tiếp theo để lấy dữ liệu thông qua vòng lập while
        }

        return listNguoiDung;
    }


    /*
    Cấu trúc
    * */

    public int deleteNguoiDung(String userName) {
        db = dbh.getWritableDatabase();
        try {                                                                                       // TRY CATCH DÙNG ĐỂ BẮT LỖI NGOẠI LỆ
            if (db.delete(TABLE_NAME, "userName=?", new String[]{userName}) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.d("ErrDelete", ex.getMessage());
        }
        return 1;
    }

    /*
    Cấu trúc
     */
    public int updateThongTinNguoiDung(NguoiDung nguoiDung) {
        db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("phone", nguoiDung.phone);
        values.put("hoVaTen", nguoiDung.hoVaTen);

        try {
            if (db.update(TABLE_NAME, values, "userName=?", new String[]{nguoiDung.userName}) == -1) {          //xác định sửa thằng nào bằng userName
                return -1;
            }
        } catch (Exception ex) {
            Log.d("ErrDelete", ex.getMessage());
        }
        return 1;
    }

    /*
    Cấu trúc
     */
    public int updateMatKhauNguoiDung(NguoiDung nguoiDung) {
        db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("passWord", nguoiDung.passWord);
        int result = db.update(TABLE_NAME, values, "userName=?", new String[]{nguoiDung.userName});

        try {
            if (result == -1) {
                return -1;
            }
        } catch (Exception e) {
            Log.e(TAG, "NguoiDungDAO" + e.toString());
        }
        return 1;
    }
}
