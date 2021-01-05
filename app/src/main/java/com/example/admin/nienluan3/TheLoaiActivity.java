package com.example.admin.nienluan3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.admin.nienluan3.Adapter.AdapterTheLoai;
import com.example.admin.nienluan3.Model.TheLoai;
import com.example.admin.nienluan3.DAO.TheLoaiDAO;
import com.example.admin.lab1_duanmau.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class TheLoaiActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    EditText edtMaTheLoai, edtTenTheLoai, edtViTri, edtMoTa;

    RecyclerView recyclerView;
    ArrayList<TheLoai> listTheLoai = new ArrayList<TheLoai>();
    TheLoaiDAO theLoaiDAO;
    AdapterTheLoai adapterTheLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);

        theLoaiDAO = new TheLoaiDAO(TheLoaiActivity.this);

        AnhXa();

        setToolbar();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatiomBt();
                themLoai();
            }
        });

        AdapterCapNhatGiaoDien();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void AnhXa() {
        floatingActionButton = findViewById(R.id.floatActionButton);
        toolbar = findViewById(R.id.toolBar);
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
        listTheLoai = theLoaiDAO.viewTheLoai();
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(TheLoaiActivity.this);
        adapterTheLoai = new AdapterTheLoai(TheLoaiActivity.this, listTheLoai);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterTheLoai);
    }

    private void themLoai() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = TheLoaiActivity.this.getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_loai, null);

        edtMaTheLoai = view1.findViewById(R.id.edtMaTheLoai);
        edtTenTheLoai = view1.findViewById(R.id.edtTenTheLoai);
        edtViTri = view1.findViewById(R.id.edtViTri);
        edtMoTa = view1.findViewById(R.id.edtMoTa);

        alertDialog.setView(view1);
        animationThemLoai();

        alertDialog.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String MaTheLoai = edtMaTheLoai.getText().toString().trim();
                String TenTheLoai = edtTenTheLoai.getText().toString().trim();
                int ViTri = 0;
                try {
                    ViTri = Integer.parseInt(edtViTri.getText().toString().trim());
                } catch (Exception e) {
                }
                String MoTa = edtMoTa.getText().toString().trim();

                if (MaTheLoai.equals("") || TenTheLoai.equals("") || ViTri <= 0 || MoTa.equals("")) {

                    themLoai();
                    edtMaTheLoai.setText(MaTheLoai);
                    edtTenTheLoai.setText(TenTheLoai);
                    edtViTri.setText(String.valueOf(ViTri));
                    edtMoTa.setText(MoTa);

                    Toasty.error(TheLoaiActivity.this, "Lỗi!", Toast.LENGTH_SHORT,true).show();

                } else {

                    TheLoai theLoai = new TheLoai(MaTheLoai, TenTheLoai, MoTa, ViTri);
                    theLoaiDAO.insertTheLoai(theLoai);
                    Toasty.success(TheLoaiActivity.this, "Đã thêm", Toast.LENGTH_SHORT,true).show();
                    AdapterCapNhatGiaoDien();

                }


            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toasty.error(getContext(), "Đã hủy", Toast.LENGTH_SHORT, true).show();
                Toasty.success(TheLoaiActivity.this, "Đã hủy!", Toast.LENGTH_SHORT,true).show();
            }
        });
        alertDialog.show();
        //dialog

    }

    private void animatiomBt() {
        Animation animationBT = AnimationUtils.loadAnimation(this, R.anim.anim_floating_action_button);
        floatingActionButton.startAnimation(animationBT);
    }

    private void animationThemLoai() {
        Animation tenDangNhapAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_one);
        edtMaTheLoai.startAnimation(tenDangNhapAnim);

        Animation matKhauAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_two);
        edtTenTheLoai.startAnimation(matKhauAnim);

        Animation matKhauXacNhanAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_three);
        edtViTri.startAnimation(matKhauXacNhanAnim);

        Animation soDienThoaiAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_four);
        edtMoTa.startAnimation(soDienThoaiAnim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter2, R.anim.anim_exit2);
    }
}
