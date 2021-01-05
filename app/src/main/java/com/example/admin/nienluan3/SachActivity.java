package com.example.admin.nienluan3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.nienluan3.Adapter.AdapterSach;
import com.example.admin.nienluan3.Adapter.AdapterSpinnerSach;
import com.example.admin.nienluan3.Model.Sach;
import com.example.admin.nienluan3.Model.TheLoai;
import com.example.admin.nienluan3.DAO.SachDAO;
import com.example.admin.nienluan3.DAO.TheLoaiDAO;
import com.example.admin.lab1_duanmau.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SachActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private TextView tvChonLoaiSach;
    private EditText edtMaSach, edtTenSach, edtTacGia, edtNhaXuatBan, edtGiaBia, edtSoLuong;

    //..
    private Spinner spLoaiSach;
    ArrayList<TheLoai> listTheLoai = new ArrayList<TheLoai>();
    TheLoaiDAO theLoaiDAO = new TheLoaiDAO(this);
    AdapterSach adapterSach;
    //..

    RecyclerView recyclerView;
    ArrayList<Sach> listSach = new ArrayList<Sach>();
    SachDAO sachDAO = new SachDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);

        AnhXa();

        setToolbar();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatiomBt();
                themSach();
            }
        });

        AdapterCapNhatGiaoDien();

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

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void animatiomBt() {
        Animation animationBT = AnimationUtils.loadAnimation(this, R.anim.anim_floating_action_button);
        floatingActionButton.startAnimation(animationBT);
    }

    public void AdapterSpinnerTheLoai() {
        listTheLoai = theLoaiDAO.viewTheLoai();
        AdapterSpinnerSach adapterSpinnerSach = new AdapterSpinnerSach(this, listTheLoai);
        spLoaiSach.setAdapter(adapterSpinnerSach);
    }

    public void AdapterCapNhatGiaoDien() {
        listSach = sachDAO.viewSach();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(SachActivity.this);
        adapterSach = new AdapterSach(SachActivity.this, listSach);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterSach);
    }

    private void themSach() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = SachActivity.this.getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_themsach, null);

        spLoaiSach = view1.findViewById(R.id.spLoaiSach);
        tvChonLoaiSach = view1.findViewById(R.id.tvChonLoaiSach);
        edtMaSach = view1.findViewById(R.id.edtMaSach);
        edtTenSach = view1.findViewById(R.id.editTenSach);
        edtTacGia = view1.findViewById(R.id.edtTacGia);
        edtNhaXuatBan = view1.findViewById(R.id.edtNhaXuatBan);
        edtGiaBia = view1.findViewById(R.id.edtGiaBia);
        edtSoLuong = view1.findViewById(R.id.edtSoLuong);


        alertDialog.setView(view1);

        animationThemSach();
        AdapterSpinnerTheLoai();

        alertDialog.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //spinner
                String maLoai = "";
                try {
                    int index = spLoaiSach.getSelectedItemPosition();
                    TheLoai theLoai = listTheLoai.get(index);
                    maLoai = theLoai.maTheLoai;
                } catch (Exception e) {
                }
                String MaSach = edtMaSach.getText().toString().trim();
                String TenSach = edtTenSach.getText().toString().trim();
                String TacGia = edtTacGia.getText().toString().trim();
                String NhaXuatBan = edtNhaXuatBan.getText().toString().trim();

                double GiaBia = 0;
                int SoLuong = 0;
                try {
                    GiaBia = Double.parseDouble(edtGiaBia.getText().toString().trim());
                    SoLuong = Integer.parseInt(edtSoLuong.getText().toString().trim());
                } catch (Exception e) {
                }


                if (MaSach.equals("") || TenSach.equals("") || TacGia.equals("") || NhaXuatBan.equals("") || GiaBia <= 0 || SoLuong <= 0) {
                    Toasty.error(SachActivity.this, "Lỗi!", Toast.LENGTH_SHORT,true).show();

                    themSach();
                    edtMaSach.setText(MaSach);
                    edtTenSach.setText(TenSach);
                    edtTacGia.setText(TacGia);
                    edtNhaXuatBan.setText(NhaXuatBan);
                    edtGiaBia.setText(String.valueOf(GiaBia));
                    edtSoLuong.setText(String.valueOf(SoLuong));
                } else {
                    Sach sach = new Sach(MaSach, maLoai, TenSach, TacGia, NhaXuatBan, GiaBia, SoLuong);
                    sachDAO.insertSach(sach);
                    Toasty.success(SachActivity.this, "Đã thêm!", Toast.LENGTH_SHORT, true).show();
                    AdapterCapNhatGiaoDien();
                }

            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toasty.success(SachActivity.this, "Đã hủy", Toast.LENGTH_SHORT, true).show();
                AdapterCapNhatGiaoDien();
            }
        });
        alertDialog.show();
        //dialog
    }

    private void animationThemSach() {
        Animation tenDangNhapAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_one);
        tvChonLoaiSach.startAnimation(tenDangNhapAnim);

        Animation matKhauAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_two);
        edtMaSach.startAnimation(matKhauAnim);

        Animation matKhauXacNhanAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_three);
        edtTenSach.startAnimation(matKhauXacNhanAnim);

        Animation soDienThoaiAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_four);
        edtTacGia.startAnimation(soDienThoaiAnim);

        Animation animNhaXuatBan = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_five);
        edtNhaXuatBan.startAnimation(animNhaXuatBan);

        Animation animGiaBia = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_six);
        edtGiaBia.startAnimation(animGiaBia);

        Animation animSoLuong = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_sevent);
        edtSoLuong.startAnimation(animSoLuong);
    }

    // Seach_View
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterSach.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    //Search_View

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter2, R.anim.anim_exit2);
    }
}
