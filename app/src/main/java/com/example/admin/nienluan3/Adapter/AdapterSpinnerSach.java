package com.example.admin.nienluan3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.nienluan3.Model.TheLoai;
import com.example.admin.lab1_duanmau.R;
import com.example.admin.nienluan3.SachActivity;

import java.util.ArrayList;

public class AdapterSpinnerSach extends BaseAdapter {
    Context context;
    ArrayList<TheLoai> listTheLoai = new ArrayList<TheLoai>();

    public AdapterSpinnerSach(Context context, ArrayList<TheLoai> listTheLoai) {
        this.context = context;
        this.listTheLoai = listTheLoai;
    }

    @Override
    public int getCount() {
        return listTheLoai.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((SachActivity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.one_item_spinner_sach, null);

        TextView tvMaTheLoaiTenTheLoai = convertView.findViewById(R.id.tvMaTheLoaiTenTheLoai);

        TheLoai theLoai = listTheLoai.get(position);

        tvMaTheLoaiTenTheLoai.setText(theLoai.maTheLoai);

        return convertView;
    }
}
