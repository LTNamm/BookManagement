package com.example.admin.nienluan3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.nienluan3.Adapter.AdapterHoaDonChiTiet;
import com.example.admin.nienluan3.Model.HoaDon;
import com.example.admin.nienluan3.Model.HoaDonChiTiet;
import com.example.admin.nienluan3.DAO.HoaDonChiTietDAO;
import com.example.admin.nienluan3.DAO.HoaDonDAO;
import com.example.admin.lab1_duanmau.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class HoaDonChiTietActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView tvTongHoaDon;

    ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<HoaDonChiTiet>();
    HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
    AdapterHoaDonChiTiet adapterHoaDonChiTiet;

    ArrayList<HoaDon> listHoaDon = new ArrayList<HoaDon>();
    HoaDonDAO hoaDonDAO = new HoaDonDAO(this);
    public String getMaHoaDon = "";

    ArrayList<HoaDonChiTiet> listHoaDonChiTiet2 = new ArrayList<HoaDonChiTiet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);

        AnhXa();
        setToolbar();

        Intent intent = getIntent();
        getMaHoaDon = intent.getStringExtra("data");
        Log.e("MaHoaDon", "------------->" + getMaHoaDon);
        //AdapterCapNhatGiaoDien();

        AdapterCapNhatGiaoDienLanHai();

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolBar);
        recyclerView = findViewById(R.id.recyclerView);
        tvTongHoaDon = findViewById(R.id.tvTongHoaDon);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void AdapterCapNhatGiaoDien() {

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(HoaDonChiTietActivity.this);
        adapterHoaDonChiTiet = new AdapterHoaDonChiTiet(HoaDonChiTietActivity.this, listHoaDonChiTiet2);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterHoaDonChiTiet);
    }

    public void AdapterCapNhatGiaoDienLanHai() {
        listHoaDon = hoaDonDAO.viewHoaDon();
        listHoaDonChiTiet = hoaDonChiTietDAO.viewHoaDonChiTiet();

        double tongHoaDon = 0.0;
        for (int i = 0; i < listHoaDon.size(); i++) {
            for (int j = 0; j < listHoaDonChiTiet.size(); j++) {
                if (listHoaDon.get(i).maHoadon.equalsIgnoreCase(listHoaDonChiTiet.get(j).maHoaDon)
                        && listHoaDonChiTiet.get(j).maHoaDon.equalsIgnoreCase(getMaHoaDon)) {
                    //Toast.makeText(this, "" + listHoaDonChiTiet.get(j).maSach, Toast.LENGTH_SHORT).show();

                    listHoaDonChiTiet2.add(new HoaDonChiTiet(listHoaDonChiTiet.get(j).maHoaDon, listHoaDonChiTiet.get(j).maSach,
                            listHoaDonChiTiet.get(j).soLuongMua, listHoaDonChiTiet.get(j).thanhTien));
                    tongHoaDon += Double.parseDouble(listHoaDonChiTiet.get(j).thanhTien);
                    AdapterCapNhatGiaoDien();
                }
            }

        }
        tvTongHoaDon.setText(NumberFormat.getInstance().format(tongHoaDon) + " vnđ");
    }

    public void XuLyTongTien() {
        listHoaDon = hoaDonDAO.viewHoaDon();
        listHoaDonChiTiet = hoaDonChiTietDAO.viewHoaDonChiTiet();

        double tongHoaDon = 0.0;
                for (int i = 0; i < listHoaDon.size(); i++) {
                    for (int j = 0; j < listHoaDonChiTiet.size(); j++) {
                        if (listHoaDon.get(i).maHoadon.equalsIgnoreCase(listHoaDonChiTiet.get(j).maHoaDon)
                                && listHoaDonChiTiet.get(j).maHoaDon.equalsIgnoreCase(getMaHoaDon)) {
                            tongHoaDon += Double.parseDouble(listHoaDonChiTiet.get(j).thanhTien);
                            AdapterCapNhatGiaoDien();
                        }
            }

        }
        tvTongHoaDon.setText(NumberFormat.getInstance().format(tongHoaDon) + " vnđ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter2, R.anim.anim_exit2);
    }
}
