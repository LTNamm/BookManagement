package com.example.admin.nienluan3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.nienluan3.Model.NguoiDung;
import com.example.admin.nienluan3.DAO.NguoiDungDAO;
import com.example.admin.lab1_duanmau.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    //dialog
    private EditText tenDangNhap, matKhau, matKhauXacNhan, soDienThoai, hoVaTen;
    //dialog

    private EditText edtTenDangNhap, edtMatKhau;
    private Button btDangNhap, btDk;
    private ImageView logoLogin;
    private CheckBox chkGhiNho;

    private SharedPreferences sharedPreferences;

    //goi list nguoi dung
    private NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);
    private ArrayList<NguoiDung> listNguoiDung = new ArrayList<NguoiDung>();
    //goi list nguoi dung

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        find_View_ById();


        /*
            Sử dụng sharedPreferences để lưu tài khoản đăng kí
         */
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        /*
            Lấy dữ liệu từ sharedPreferences
         */
        try {
            edtTenDangNhap.setText(sharedPreferences.getString("tenDangNhap", ""));
            edtMatKhau.setText(sharedPreferences.getString("matKhau", ""));
            chkGhiNho.setChecked(sharedPreferences.getBoolean("kiemTra", false));
        } catch (Exception e) {
            Log.d("Loishare", e.toString());
        }


        btDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evenDangNhap();

                animationButtonZoomLogin();
            }
        });

        btDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evenDangKi();
                animationButtonZoomHuy();
            }
        });

    }

    private void evenDangKi() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = LoginActivity.this.getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_dk_nguoidung, null);

        tenDangNhap = view1.findViewById(R.id.edtTenDangNhap);
        matKhau = view1.findViewById(R.id.edtMatKhau);
        matKhauXacNhan = view1.findViewById(R.id.edtMatKhauXacNhan);
        soDienThoai = view1.findViewById(R.id.edtSoDienThoai);
        hoVaTen = view1.findViewById(R.id.edtHoVaTen);

        alertDialog.setView(view1);

        animationDialogItem();

        alertDialog.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                    .trim bỏ khoảng trắng đầu đuôi
                * */
                String edtTenDangNhapDK = tenDangNhap.getText().toString().trim();
                String edtMatKhauDK = matKhau.getText().toString().trim();
                String edtMatKhauXacNhan = matKhauXacNhan.getText().toString().trim();
                String edtSoDienThoai = soDienThoai.getText().toString().trim();
                String edtHoVaTen = hoVaTen.getText().toString().trim();

                //.., kiem tra ten dang nhap co trung nhau hay khong
                listNguoiDung = nguoiDungDAO.viewNguoiDung();
                boolean kt = false;
                for (NguoiDung nguoiDung : listNguoiDung) {
                    if (edtTenDangNhapDK.equals(nguoiDung.userName)) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }
                //...kiem tra ten dang nhap co trung nhay hay khong

                //sqlite
                NguoiDung nguoiDung = new NguoiDung(edtTenDangNhapDK, edtMatKhauDK, edtSoDienThoai, edtHoVaTen);
                if (!edtMatKhauDK.equals(edtMatKhauXacNhan)
                        || edtTenDangNhapDK.equals("")
                        || edtMatKhauDK.equals("")
                        || edtSoDienThoai.equals("")
                        || edtHoVaTen.equals("")
                        || kt == true
                        ) {

                    evenDangKi();
                    tenDangNhap.setText(edtTenDangNhapDK);
                    matKhau.setText(edtMatKhauDK);
                    matKhauXacNhan.setText(edtMatKhauXacNhan);
                    soDienThoai.setText(edtSoDienThoai);
                    hoVaTen.setText(edtHoVaTen);

                    Toasty.error(LoginActivity.this, "Lỗi!", Toast.LENGTH_SHORT, true).show();

                } else {

                    nguoiDungDAO.insertNguoiDung(nguoiDung);

                    edtTenDangNhap.setText("");
                    edtMatKhau.setText("");
                    edtTenDangNhap.setText(edtTenDangNhapDK);
                    edtMatKhau.setText(edtMatKhauDK);

                    listNguoiDung = nguoiDungDAO.viewNguoiDung();
                    Toasty.success(LoginActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT, true).show();

                }
                //sqlite

                //TastyToast.makeText(getContext(), "Đ !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }
        });


        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toasty.success(LoginActivity.this, "Đã hủy!", Toast.LENGTH_SHORT, true).show();
            }
        });
        alertDialog.show();
    }

    /*
        Sự kiện quản lí đăng nhập
     */
    private void evenDangNhap() {
        /*
            Lấy tài khoản và mật khẩu từ edt
         */
        String tenDangNhap = edtTenDangNhap.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();

        /*

         */
        boolean kt = false;
        /*
            Kiểm tra tên người dùng và mật khẩu có tồn tại trong mảng hay không
         */
        for (NguoiDung nguoiDung : listNguoiDung) {
            /*
                So sánh tên đăng nhập và mật khẩu trong share có trùng hay không
            * */

            if (tenDangNhap.equals(nguoiDung.userName) && matKhau.equals(nguoiDung.passWord)) {
                kt = true;
                break;
            } else {
                kt = false;
            }
        }


        if (kt == true) {

            /*
                Kiểm tra người dùng có click "Nhớ tài khoản hay không"
             */
            if (chkGhiNho.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tenDangNhap", tenDangNhap);
                editor.putString("matKhau", matKhau);
                editor.putBoolean("kiemTra", true);
                editor.commit();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("tenDangNhap");
                editor.remove("matKhau");
                editor.remove("kiemTra");
                editor.commit();
            }


            /*
                Chuyển màn hình từ LoginActivity tới MainActivity
             */
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            /*
                Đóng màn hình finish();
             */
            finish();

            Toasty.success(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT, true).show();
        }

    }

    private void find_View_ById() {
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        btDangNhap = findViewById(R.id.btnDangNhap);
        btDk = findViewById(R.id.btnDk);
        logoLogin = findViewById(R.id.logoLogin);
        chkGhiNho = findViewById(R.id.chkGhiNho);
        listNguoiDung = nguoiDungDAO.viewNguoiDung();

    }


    private void animationButtonZoomLogin() {
        Animation animationBT = AnimationUtils.loadAnimation(this, R.anim.anim_zoom_button_login);
        btDangNhap.startAnimation(animationBT);
    }

    private void animationButtonZoomHuy() {
        Animation animationBT = AnimationUtils.loadAnimation(this, R.anim.anim_zoom_button_login);
        btDk.startAnimation(animationBT);
    }

    private void animationDialogItem() {
        Animation tenDangNhapAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_one);
        tenDangNhap.startAnimation(tenDangNhapAnim);

        Animation matKhauAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_two);
        matKhau.startAnimation(matKhauAnim);

        Animation matKhauXacNhanAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_three);
        matKhauXacNhan.startAnimation(matKhauXacNhanAnim);

        Animation soDienThoaiAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_four);
        soDienThoai.startAnimation(soDienThoaiAnim);

        Animation hoVaTenAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_five);
        hoVaTen.startAnimation(hoVaTenAnim);
    }

}
