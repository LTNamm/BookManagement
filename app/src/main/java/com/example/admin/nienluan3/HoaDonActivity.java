package com.example.admin.nienluan3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.nienluan3.Adapter.AdapterHoaDon;
import com.example.admin.nienluan3.Adapter.AdapterHoaDonChiTietTam;
import com.example.admin.nienluan3.Model.HoaDon;
import com.example.admin.nienluan3.Model.HoaDonChiTiet;
import com.example.admin.nienluan3.Model.Sach;
import com.example.admin.nienluan3.ModelTam.modelHoaDonChiTietTam;
import com.example.admin.nienluan3.DAO.HoaDonChiTietDAO;
import com.example.admin.nienluan3.DAO.HoaDonDAO;
import com.example.admin.nienluan3.DAO.SachDAO;
import com.example.admin.lab1_duanmau.R;

import java.text.NumberFormat;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class HoaDonActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    private AutoCompleteTextView edtMaSach;
    private EditText edtMaHoaDon, edtMaHoaDon2, edtSoLuong;
    private Button btnThemVaoHoaDonChiTiet;
    private TextView tvChonNgay;
    private DatePicker datePicker;
    private String toDate = "";
    private Integer selectedYear, selectedMonth, selectedDayOfMonth;

    //..hoa don
    RecyclerView recyclerView;
    ArrayList<HoaDon> listHoaDon = new ArrayList<HoaDon>();
    HoaDonDAO hoaDonDAO = new HoaDonDAO(this);
    AdapterHoaDon adapterHoaDon;
    String maHoaDon;
    //...hoa don

    //..Hoa don chi tiet
    HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
    ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<HoaDonChiTiet>();
    RecyclerView recyclerViewhdt;
    //..Hoa don chi tiet

    //.getList Sach
    ArrayList<Sach> listSach = new ArrayList<Sach>();
    SachDAO sachDAO = new SachDAO(HoaDonActivity.this);
    //getListSach

    //..hoa don chi tiet tam
    ArrayList<modelHoaDonChiTietTam> listhdcttam = new ArrayList<modelHoaDonChiTietTam>();
    AdapterHoaDonChiTietTam adapterHoaDonChiTietTam;
    String maSach;
    int soLuong = 0;
    double giaBia = 0, Tong = 0;

    //..hoa don chi tiet tam
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        AnhXa();
        setToolbar();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                animatiomBt();
                themHoaDon();
                listhdcttam.clear();
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

    public void AdapterCapNhatGiaoDien() {
        listHoaDon = hoaDonDAO.viewHoaDon();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(HoaDonActivity.this);
        adapterHoaDon = new AdapterHoaDon(HoaDonActivity.this, listHoaDon);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterHoaDon);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void themHoaDon() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = HoaDonActivity.this.getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_themhoadon, null);

        edtMaHoaDon = view1.findViewById(R.id.edtMaHoaDon);
        tvChonNgay = view1.findViewById(R.id.tvChonNgay);
        datePicker = view1.findViewById(R.id.datePicker);

        alertDialog.setView(view1);

        animationDialogItemThemHoaDon();

        alertDialog.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //get Date
                selectedDayOfMonth = datePicker.getDayOfMonth();
                selectedMonth = datePicker.getMonth() + 1;
                selectedYear = datePicker.getYear();
                toDate = selectedDayOfMonth + "/" + selectedMonth + "/" + selectedYear;
                //get Date

                maHoaDon = edtMaHoaDon.getText().toString().trim();

                //..
                boolean kt = false;
                for (HoaDon hoaDon : listHoaDon) {
                    if (hoaDon.maHoadon.equals(maHoaDon)) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }
                //...

                if (maHoaDon.equals("") || kt == true) {
                    Toasty.error(HoaDonActivity.this, "Lỗi!", Toast.LENGTH_SHORT, true).show();
                    themHoaDon();
                } else {
                    HoaDon hoaDon = new HoaDon(maHoaDon, toDate);
                    hoaDonDAO.insertHoaDon(hoaDon);
                    //TastyToast.makeText(getContext(), "Đ !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    themHoaDonChiTiet();
                    AdapterCapNhatGiaoDien();
                    Toasty.success(HoaDonActivity.this, "Đã thêm!", Toast.LENGTH_SHORT, true).show();

                }

            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toasty.success(HoaDonActivity.this, "Đã hủy!", Toast.LENGTH_SHORT, true).show();
                AdapterCapNhatGiaoDien();
            }
        });
        alertDialog.show();
        //dialog

    }

    private void themHoaDonChiTiet() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = HoaDonActivity.this.getLayoutInflater();
        View view2 = inf.inflate(R.layout.dialog_for_themhoadonchitiet, null);

        edtMaHoaDon2 = view2.findViewById(R.id.edtMaHoaDon);
        edtSoLuong = view2.findViewById(R.id.edtSoLuong);
        btnThemVaoHoaDonChiTiet = view2.findViewById(R.id.btnThemVaoHoaDonChiTiet);

        //...auto complite EditText.
        //..get data from sachDb
        listSach = sachDAO.viewSach();
        edtMaSach = view2.findViewById(R.id.edtMaSach);
        ArrayList<String> listMS = new ArrayList<String>();
        for (Sach sach : listSach) {
            listMS.add(sach.maSach);
        }
        //Collections.sort(listMS, String.CASE_INSENSITIVE_ORDER);//Xap xep theo thu tu
        //get data sachDb

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listMS);
        edtMaSach.setAdapter(adapter);
        edtMaSach.setThreshold(1);
        edtMaSach.setDropDownAnchor(R.id.tvAnchor);
        edtMaSach.setDropDownHeight(300);
        //.. auto complite EditText

        edtMaHoaDon2.setText(maHoaDon);

        btnThemVaoHoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSach = sachDAO.viewSach();

                maSach = edtMaSach.getText().toString().trim();
                try {
                    soLuong = Integer.parseInt(edtSoLuong.getText().toString().trim());
                } catch (Exception e) {
                }
                String maHoaDon2 = edtMaHoaDon2.getText().toString().trim();
                Tong = 0.0;
                giaBia = 0.0;
                for (int i = 0; i < listSach.size(); i++) {
                    if (maSach.equalsIgnoreCase(listSach.get(i).maSach)) {
                        giaBia = listSach.get(i).giaBia;
                        Tong = giaBia * soLuong;
                    }
                }

                //..kt neu ma sach khong co trong listsach
                boolean kt = false;
                for (Sach sach : listSach) {
                    if (sach.maSach.equals(maSach)) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }
                //..kt new ma sach khong co trong list sach

                if (kt == false || soLuong <= 0) {
                    Toasty.error(HoaDonActivity.this, "Lỗi!", Toast.LENGTH_SHORT, true).show();
                } else {

                    ///add vao hoa dialog hoa don chi tiet
                    listhdcttam.add(new modelHoaDonChiTietTam(maSach, soLuong, giaBia, Tong));
                    //.. add vao dialog hoa don chi tiet

                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(maHoaDon2, maSach, soLuong, String.valueOf(Tong));
                    hoaDonChiTietDAO.insertHoaDonChiTiet(hoaDonChiTiet);
                    // ...
                }

                edtMaSach.setText("");
                edtSoLuong.setText("");

                Toasty.success(HoaDonActivity.this, "Hoàn tất! ", Toast.LENGTH_SHORT, true).show();

            }
        });


        alertDialog.setView(view2);

        animationDialogItemThemHoaDonChiTiet();

        alertDialog.setPositiveButton("Hoàn tất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toasty.error(getContext(), "Đã hủy", Toast.LENGTH_SHORT, true).show();
                if (listhdcttam.size() <= 0) {
                    Toasty.error(HoaDonActivity.this, "Bạn chưa nhập hóa đơn nào!", Toast.LENGTH_LONG, true).show();
                    themHoaDonChiTiet();
                } else {
                    dialogViewHoaDonChiTieet();
                }
                //AdapterCapNhatGiaoDien();
            }
        });
        alertDialog.show();
        //dialog
    }

    private void animationDialogItemThemHoaDon() {
        Animation tenDangNhapAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_one);
        edtMaHoaDon.startAnimation(tenDangNhapAnim);

        Animation matKhauAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_two);
        tvChonNgay.startAnimation(matKhauAnim);

        Animation matKhauXacNhanAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_three);
        datePicker.startAnimation(matKhauXacNhanAnim);
    }

    private void animationDialogItemThemHoaDonChiTiet() {
        Animation tenDangNhapAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_one);
        edtMaHoaDon2.startAnimation(tenDangNhapAnim);

        Animation matKhauAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_two);
        edtMaSach.startAnimation(matKhauAnim);

        Animation matKhauXacNhanAnim = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_three);
        edtSoLuong.startAnimation(matKhauXacNhanAnim);

        Animation btn2 = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_item_five);
        btnThemVaoHoaDonChiTiet.startAnimation(btn2);


    }

    private void animatiomBt() {
        Animation animationBT = AnimationUtils.loadAnimation(this, R.anim.anim_floating_action_button);
        floatingActionButton.startAnimation(animationBT);
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
                adapterHoaDon.getFilter().filter(newText);
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

    private void dialogViewHoaDonChiTieet() {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inf = HoaDonActivity.this.getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_hoa_don_chi_tiet_tam, null);

        TextView tvTongHoaDon = view1.findViewById(R.id.tvTongHoaDon);
        recyclerViewhdt = view1.findViewById(R.id.recyclerView);

        double tongTienTam = 0;
        for (modelHoaDonChiTietTam tam : listhdcttam) {
            tongTienTam += tam.thanhTien;
        }
        tvTongHoaDon.setText(NumberFormat.getInstance().format(tongTienTam) + " vnđ");

        AdapterHoaDonChiTietTam();
        alertDialog.setView(view1);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toasty.error(getContext(), "Đã hủy", Toast.LENGTH_SHORT, true).show();
                AdapterCapNhatGiaoDien();
            }
        });
        alertDialog.show();
        //dialog
    }

    private void AdapterHoaDonChiTietTam() {
        LinearLayoutManager manager = new LinearLayoutManager(HoaDonActivity.this);
        adapterHoaDonChiTietTam = new AdapterHoaDonChiTietTam(HoaDonActivity.this, listhdcttam);
        recyclerViewhdt.setLayoutManager(manager);
        recyclerViewhdt.setAdapter(adapterHoaDonChiTietTam);
    }
}
