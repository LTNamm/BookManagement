package com.example.admin.nienluan3.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.example.admin.nienluan3.HoaDonChiTietActivity;
import com.example.admin.nienluan3.Model.HoaDon;
import com.example.admin.nienluan3.Model.HoaDonChiTiet;
import com.example.admin.nienluan3.Model.Sach;
import com.example.admin.lab1_duanmau.R;
import com.example.admin.nienluan3.DAO.HoaDonChiTietDAO;
import com.example.admin.nienluan3.DAO.HoaDonDAO;
import com.example.admin.nienluan3.DAO.SachDAO;

import java.text.NumberFormat;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AdapterHoaDonChiTiet extends RecyclerView.Adapter<AdapterHoaDonChiTiet.MyHolder> {

    Context context;
    ArrayList<HoaDonChiTiet> listHoaDonChiTiet2 = new ArrayList<HoaDonChiTiet>();

    public AdapterHoaDonChiTiet(Context context, ArrayList<HoaDonChiTiet> listHoaDonChiTiet) {
        this.context = context;
        this.listHoaDonChiTiet2 = listHoaDonChiTiet;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_item_hoa_don_chitiet, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        final HoaDonChiTiet hoaDonChiTiet = listHoaDonChiTiet2.get(position);

        holder.tvMaSach.setText("Mã sách: " + hoaDonChiTiet.maSach);
        //...


        ArrayList<Sach> listSach = new ArrayList<Sach>();
        SachDAO sachDAO = new SachDAO(context);
        listSach = sachDAO.viewSach();
        for (int i = 0; i < listSach.size(); i++) {
            if (hoaDonChiTiet.maSach.equalsIgnoreCase(listSach.get(i).maSach)) {
                holder.tvGiaBia.setText("Giá bìa: " + NumberFormat.getInstance().format(listSach.get(i).giaBia) + " vnđ");
            }
        }
        //..

        holder.tvSoLuong.setText("Số lượng: " + String.valueOf(hoaDonChiTiet.soLuongMua));
        holder.tvThanhTien.setText("Thành tiền: " + NumberFormat.getInstance().format(Double.parseDouble(hoaDonChiTiet.thanhTien)) + " vnđ");


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


                                //...delete hoa don chi tiet

                                //...Lay du lieu tu hoaDon va HoaDonChiTiet
                                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                                ArrayList<HoaDon> listHoaDon = new ArrayList<HoaDon>();
                                listHoaDon = hoaDonDAO.viewHoaDon();

                                HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
                                ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<HoaDonChiTiet>();
                                listHoaDonChiTiet = hoaDonChiTietDAO.viewHoaDonChiTiet();
                                //..Lay du lieu tu listHoaDon va HoaDonChiTiet

                                int index = 0;
                                HoaDonChiTiet hoaDonChiTiet2 = listHoaDonChiTiet2.get(position);

                                for (int i = 0; i < listHoaDon.size(); i++) {
                                    for (int j = 0; j < listHoaDonChiTiet.size(); j++) {
                                        if (listHoaDon.get(i).maHoadon.equalsIgnoreCase(listHoaDonChiTiet.get(j).maHoaDon)
                                                && listHoaDonChiTiet.get(j).maHoaDon.equalsIgnoreCase(((HoaDonChiTietActivity) context).getMaHoaDon)
                                                && hoaDonChiTiet2.maSach.equalsIgnoreCase(listHoaDonChiTiet.get(j).maSach)
                                                && hoaDonChiTiet2.soLuongMua == listHoaDonChiTiet.get(j).soLuongMua) {
                                            index = listHoaDonChiTiet.get(j).maHoaDonChiTiet;
                                            //Log.e("XXXX", listHoaDonChiTiet.get(i).maHoaDonChiTiet+"");
                                        }
                                    }
                                }
                                Log.e("Adatper", "" + index);

                                hoaDonChiTietDAO.deleteHoaDonChiTiet(index);
                                listHoaDonChiTiet2.remove(hoaDonChiTiet2.maHoaDonChiTiet);

                                ((HoaDonChiTietActivity) context).AdapterCapNhatGiaoDienLanHai();
                                ((HoaDonChiTietActivity) context).AdapterCapNhatGiaoDien();
                                try {
                                    for (int h = 0; h < listHoaDonChiTiet2.size(); h++) {
                                        listHoaDonChiTiet2.remove(hoaDonChiTiet2.maHoaDonChiTiet);
                                    }
                                } catch (Exception e) {
                                }

                                ((HoaDonChiTietActivity) context).XuLyTongTien();
                                Toasty.success(context, "Hoàn tất!", Toast.LENGTH_SHORT, true).show();
                                //...delete hoa don chi tiet
                                // click
                            }
                        })
                        .setNegativeButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                //click
                                ((HoaDonChiTietActivity) context).XuLyTongTien();
                                Toasty.success(context, "Đã Hủy", Toast.LENGTH_LONG, true).show();
                            }
                        })
                        .show();
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
        return listHoaDonChiTiet2.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvMaSach, tvSoLuong, tvGiaBia, tvThanhTien;
        public ImageView imgDelete, icon_Swipe;

        public MyHolder(View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvGiaBia = itemView.findViewById(R.id.tvGiaBia);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            icon_Swipe = itemView.findViewById(R.id.icon_swipe);
        }
    }
}
