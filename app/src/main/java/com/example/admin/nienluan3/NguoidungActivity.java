package com.example.admin.nienluan3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.nienluan3.Adapter.AdapterNguoiDung;
import com.example.admin.nienluan3.Model.NguoiDung;
import com.example.admin.nienluan3.DAO.NguoiDungDAO;
import com.example.admin.lab1_duanmau.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class NguoidungActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    EditText tenDangNhap, matKhau, matKhauXacNhan, soDienThoai, hoVaTen;
    EditText tenDangNhapDoiMk, matKhauMoi, matKhauCu, xacNhanMatKhauMoi;

    RecyclerView recyclerView;
    AdapterNguoiDung adapterNguoiDung;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung = new ArrayList<NguoiDung>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoidung);

        nguoiDungDAO = new NguoiDungDAO(NguoidungActivity.this);

        AnhXa();

        setToolbar();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatiomBt();
                dialogThem();
            }
        });

        AdapterCapNhatGiaoDien();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nguoidung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.dangXuat) {
            Intent intent = new Intent(NguoidungActivity.this, LoginActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_enter2, R.anim.anim_exit2);
        }
        if (item.getItemId() == R.id.doiMatKhau) {
            dialogDoiMatKhau();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolBar);
        floatingActionButton = findViewById(R.id.floatActionButton);
        recyclerView = findViewById(R.id.recyclerView);
        //..hide and show floating action button
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    floatingActionButton.hide();
                    floatingActionButton.setVisibility(View.INVISIBLE);
                } else if (dy < 0) {
                    floatingActionButton.show();
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            }
        });
        //..hide and show floating action button

    }

    public void AdapterCapNhatGiaoDien() {
        listNguoiDung = nguoiDungDAO.viewNguoiDung();
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(NguoidungActivity.this);
        adapterNguoiDung = new AdapterNguoiDung(NguoidungActivity.this, listNguoiDung);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterNguoiDung);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void dialogThem() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = NguoidungActivity.this.getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_nguoidung, null);

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
                String edtTenDangNhap = tenDangNhap.getText().toString().trim();
                String edtMatKhau = matKhau.getText().toString().trim();
                String edtMatKhauXacNhan = matKhauXacNhan.getText().toString().trim();
                String edtSoDienThoai = soDienThoai.getText().toString().trim();
                String edtHoVaTen = hoVaTen.getText().toString().trim();

                //...kiem tra ten nguoi dung trung nhau
                boolean kt = false;
                listNguoiDung = nguoiDungDAO.viewNguoiDung();
                for (NguoiDung nguoiDung : listNguoiDung) {
                    if (nguoiDung.userName.equals(edtTenDangNhap)) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }
                //..,kiem tra ten nguoi dung trung nhau

                //sqlite
                NguoiDung nguoiDung = new NguoiDung(edtTenDangNhap, edtMatKhau, edtSoDienThoai, edtHoVaTen);
                if (kt == true || edtTenDangNhap.equals("") || !edtMatKhau.equals(edtMatKhauXacNhan)
                        || edtSoDienThoai.equals("") || edtHoVaTen.equals("")) {

                    Toasty.error(getApplicationContext(), "Lỗi!", Toast.LENGTH_SHORT, true).show();
                    dialogThem();
                    tenDangNhap.setText(edtTenDangNhap);
                    matKhau.setText(edtMatKhau);
                    matKhauXacNhan.setText(edtMatKhauXacNhan);
                    soDienThoai.setText(edtSoDienThoai);
                    hoVaTen.setText(edtHoVaTen);

                } else {
                    nguoiDungDAO.insertNguoiDung(nguoiDung);
                    Toasty.success(getApplicationContext(), "Đã Thêm!", Toast.LENGTH_SHORT, true).show();
                }
                //sqlite

                //Toasty.success(getContext(), "Đã thêm!", Toast.LENGTH_SHORT, true).show();
                //TastyToast.makeText(getContext(), "Đ !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                AdapterCapNhatGiaoDien();
            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toasty.success(getApplicationContext(), "Đã hủy", Toast.LENGTH_SHORT, true).show();
            }
        });
        alertDialog.show();
        //dialog
    }

    private void dialogDoiMatKhau() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = NguoidungActivity.this.getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_doimatkhau, null);

        tenDangNhapDoiMk = view1.findViewById(R.id.edtTenDangNhapDoiMk);
        matKhauCu = view1.findViewById(R.id.edtMatKhauCu);
        matKhauMoi = view1.findViewById(R.id.edtMatKhauMoi);
        xacNhanMatKhauMoi = view1.findViewById(R.id.edtXacNhanMatKhauMoi);

        alertDialog.setView(view1);

        animationDX();

        alertDialog.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenDangNhapDoiMK = tenDangNhapDoiMk.getText().toString().trim();
                String mkCu = matKhauCu.getText().toString().trim();
                String mkMoi = matKhauMoi.getText().toString().trim();
                String mkXacNhan = xacNhanMatKhauMoi.getText().toString().trim();

                //xy ly doi mat khau
                boolean kt = false;
                for (int i = 0; i < listNguoiDung.size(); i++) {
                    if (tenDangNhapDoiMK.equals(listNguoiDung.get(i).userName) && mkCu.equals(listNguoiDung.get(i).passWord)) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }

                if (kt == true && !mkCu.equals(mkMoi) && mkMoi.equals(mkXacNhan)) {
                    NguoiDung nguoiDung = new NguoiDung(tenDangNhapDoiMK, mkXacNhan);
                    nguoiDungDAO.updateMatKhauNguoiDung(nguoiDung);
                    Toasty.success(NguoidungActivity.this, "Thành công", Toast.LENGTH_LONG, true).show();
                } else {
                    dialogDoiMatKhau();
                    tenDangNhapDoiMk.setText(tenDangNhapDoiMK);
                    matKhauCu.setText(mkCu);
                    matKhauMoi.setText(mkMoi);
                    xacNhanMatKhauMoi.setText(mkXacNhan);
                    Toasty.error(NguoidungActivity.this, "Lỗi!", Toast.LENGTH_LONG).show();
                }
                //Xy ly doi mat khau

                //Toasty.success(getContext(), "Đã thêm!", Toast.LENGTH_SHORT, true).show();
                //TastyToast.makeText(getContext(), "Đ !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toasty.error(getContext(), "Đã hủy", Toast.LENGTH_SHORT, true).show();
                Toasty.success(NguoidungActivity.this, "Đã hủy!", Toast.LENGTH_SHORT, true).show();
            }
        });
        alertDialog.show();
        //dialog
    }

    private void animatiomBt() {
        Animation animationBT = AnimationUtils.loadAnimation(this, R.anim.anim_floating_action_button);
        floatingActionButton.startAnimation(animationBT);
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

    private void animationDX() {
        Animation tenDangNhapAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_one);
        matKhauCu.startAnimation(tenDangNhapAnim);

        Animation matKhauAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_two);
        matKhauMoi.startAnimation(matKhauAnim);

        Animation matKhauXacNhanAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_three);
        xacNhanMatKhauMoi.startAnimation(matKhauXacNhanAnim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter2, R.anim.anim_exit2);
    }
}
