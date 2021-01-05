package com.example.admin.nienluan3.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.example.admin.nienluan3.Model.Sach;
import com.example.admin.nienluan3.Model.TheLoai;
import com.example.admin.lab1_duanmau.R;
import com.example.admin.nienluan3.DAO.SachDAO;
import com.example.admin.nienluan3.DAO.TheLoaiDAO;
import com.example.admin.nienluan3.SachActivity;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AdapterSach extends RecyclerView.Adapter<AdapterSach.MyHolder> implements Filterable {

    Context context;
    ArrayList<Sach> listSach = new ArrayList<Sach>();
    ArrayList<Sach> listSachFull;

    public AdapterSach(Context context, ArrayList<Sach> listSach) {
        this.context = context;
        this.listSach = listSach;
        listSachFull = new ArrayList<>(listSach);
    }

    //.Spinner
    ArrayList<TheLoai> listTheLoai = new ArrayList<TheLoai>();
    //Spinner

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_item_sach, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.tvMaSach.setText("Mã sách: " + listSach.get(position).maSach);
        holder.tvSoLuong.setText("Số lượng: " + listSach.get(position).soLuong);

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AwesomeSuccessDialog(context)
                        .setTitle("Bạn có chắc muốn xóa ?")
                        .setMessage("Dữ liệu sẽ không thể phục hồi.")
                        .setColoredCircle(R.color.dialogErrorBackgroundColor)
                        .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                        .setCancelable(true)
                        .setPositiveButtonText("OK")
                        .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                        .setPositiveButtonTextColor(R.color.white)
                        .setNegativeButtonText("Hủy")
                        .setNegativeButtonbackgroundColor(R.color.dialogErrorBackgroundColor)
                        .setNegativeButtonTextColor(R.color.white)
                        .setPositiveButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                // click
                                SachDAO sachDAO = new SachDAO(context);
                                sachDAO.deleteSach(listSach.get(position).maSach);
                                ((SachActivity) context).AdapterCapNhatGiaoDien();
                                Toasty.success(context, "Đã xóa", Toast.LENGTH_LONG, true).show();
                                // click
                            }
                        })
                        .setNegativeButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                //click
                                ((SachActivity) context).AdapterCapNhatGiaoDien();
                                Toasty.success(context, "Đã Hủy", Toast.LENGTH_LONG, true).show();
                            }
                        })
                        .show();


            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evenEdit(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation matKhauAnim = AnimationUtils.loadAnimation(context, R.anim.anim_icon_swipe_open);
                holder.icon_Swipe.startAnimation(matKhauAnim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSach.size();
    }

    @Override
    public Filter getFilter() {
        return SachFilter;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView imgDelete, imgEdit, icon_Swipe;
        public TextView tvMaSach, tvSoLuong;

        public MyHolder(View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            icon_Swipe = itemView.findViewById(R.id.icon_swipe);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
        }
    }


    //Ham xy ly khi tim kiem
    private Filter SachFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Sach> listSach = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                listSach.addAll(listSachFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Sach item : listSachFull) {
                    if (item.maSach.toLowerCase().contains(filterPattern)) {
                        listSach.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listSach;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listSach.clear();
            listSach.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    //Ham xy ly khi tim kiem

    private void evenEdit(final int position) {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(((SachActivity) context));
        LayoutInflater inf = ((SachActivity) context).getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_update_sach, null);

        final Spinner spLoaiSach = view1.findViewById(R.id.spLoaiSach);
        final EditText edtMaSach = view1.findViewById(R.id.edtMaSach);
        final EditText edtTenSach = view1.findViewById(R.id.editTenSach);
        final EditText edtTacGia = view1.findViewById(R.id.edtTacGia);
        final EditText edtNhaXuatBan = view1.findViewById(R.id.edtNhaXuatBan);
        final EditText edtGiaBia = view1.findViewById(R.id.edtGiaBia);
        final EditText edtSoLuong = view1.findViewById(R.id.edtSoLuong);

        //..Spinner
        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(((SachActivity) context));
        listTheLoai = theLoaiDAO.viewTheLoai();
        AdapterSpinnerSach adapterSpinnerSach = new AdapterSpinnerSach(context, listTheLoai);
        spLoaiSach.setAdapter(adapterSpinnerSach);
        //Spinner

        //..selection spinner
        int IndexSpinner = 0;
        for (int i = 0; i <= listTheLoai.size() - 1; i++) {
            if (listSach.get(position).maTheLoai.equalsIgnoreCase(listTheLoai.get(i).maTheLoai)) {
                IndexSpinner = i;
            }
        }
        spLoaiSach.setSelection(IndexSpinner);
        //..selection spinner

        Sach sach = listSach.get(position);
        edtMaSach.setText(sach.maSach);
        edtTenSach.setText(sach.tenSach);
        edtTacGia.setText(sach.tacGia);
        edtNhaXuatBan.setText(sach.NXB);
        edtGiaBia.setText(String.valueOf(sach.giaBia));
        edtSoLuong.setText(String.valueOf(sach.soLuong));

        alertDialog.setView(view1);

        alertDialog.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
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
                //spinner

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

                if (TenSach.equals("") || TacGia.equals("") || NhaXuatBan.equals("") || GiaBia <= 0 || SoLuong <= 0) {
                    evenEdit(position);
                    Toasty.error(context, "Lỗi!", Toast.LENGTH_SHORT, true).show();

                } else {
                    SachDAO sachDAO = new SachDAO(context);
                    Sach sach1 = new Sach(listSach.get(position).maSach, maLoai, TenSach, TacGia, NhaXuatBan, GiaBia, SoLuong);
                    sachDAO.updateSach(sach1);
                    Toasty.success(context, "Đã thêm!", Toast.LENGTH_SHORT, true).show();
                    //TastyToast.makeText(getContext(), "Đ !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    ((SachActivity) context).AdapterCapNhatGiaoDien();
                }
            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toasty.success(context, "Đã hủy!", Toast.LENGTH_SHORT, true).show();
                ((SachActivity) context).AdapterCapNhatGiaoDien();
            }
        });
        alertDialog.show();
        //dialog
    }
}
