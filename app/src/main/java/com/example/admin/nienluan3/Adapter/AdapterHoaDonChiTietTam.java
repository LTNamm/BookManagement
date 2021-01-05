package com.example.admin.nienluan3.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.nienluan3.ModelTam.modelHoaDonChiTietTam;
import com.example.admin.lab1_duanmau.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterHoaDonChiTietTam extends RecyclerView.Adapter<AdapterHoaDonChiTietTam.MyHolder> {
    Context context;
    ArrayList<modelHoaDonChiTietTam> listhdcttam = new ArrayList<modelHoaDonChiTietTam>();

    public AdapterHoaDonChiTietTam(Context context, ArrayList<modelHoaDonChiTietTam> listhdcttam) {
        this.context = context;
        this.listhdcttam = listhdcttam;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_item_hoa_don_chi_tiet_tam, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvMaSach.setText("Mã sách: "+listhdcttam.get(position).maSach);
        holder.tvSoLuong.setText("Số lương: "+listhdcttam.get(position).soLuong);
        holder.tvThanhTien.setText("Thành tiền: "+NumberFormat.getInstance().format(listhdcttam.get(position).thanhTien) + " vnđ");
        holder.tvGiaBia.setText("Giá bìa: "+NumberFormat.getInstance().format(listhdcttam.get(position).giaBia) + " vnđ");
    }

    @Override
    public int getItemCount() {
        return listhdcttam.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvMaSach, tvSoLuong, tvGiaBia, tvThanhTien;

        public MyHolder(View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvGiaBia = itemView.findViewById(R.id.tvGiaBia);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
        }
    }
}
