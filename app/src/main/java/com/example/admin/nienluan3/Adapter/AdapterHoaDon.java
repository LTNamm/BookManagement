package com.example.admin.nienluan3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.example.admin.nienluan3.HoaDonActivity;
import com.example.admin.nienluan3.HoaDonChiTietActivity;
import com.example.admin.nienluan3.Model.HoaDon;
import com.example.admin.nienluan3.Model.HoaDonChiTiet;
import com.example.admin.lab1_duanmau.R;
import com.example.admin.nienluan3.DAO.HoaDonChiTietDAO;
import com.example.admin.nienluan3.DAO.HoaDonDAO;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.Myholder> implements Filterable {
    Context context;
    ArrayList<HoaDon> listHoaDon = new ArrayList<HoaDon>();
    ArrayList<HoaDon> listHoaDonFull;

    public AdapterHoaDon(Context context, ArrayList<HoaDon> listHoaDon) {
        this.context = context;
        this.listHoaDon = listHoaDon;
        listHoaDonFull = new ArrayList<HoaDon>(listHoaDon);
}

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_item_hoa_don, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myholder holder, final int position) {
        HoaDon hoaDon = listHoaDon.get(position);

        holder.tvNgayMua.setText(hoaDon.ngayMua);
        holder.tvMaHoaDon.setText(hoaDon.maHoadon);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
                ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<HoaDonChiTiet>();
                listHoaDonChiTiet = hoaDonChiTietDAO.viewHoaDonChiTiet();

                boolean kt = true
                        ;
                for (int i = 0; i < listHoaDonChiTiet.size(); i++) {
                    if (listHoaDon.get(position).maHoadon.equalsIgnoreCase(listHoaDonChiTiet.get(i).maHoaDon)) {
                        kt = false;
                        break;
                    } else {
                        kt = true;
                    }
                }

                if (kt == false) {
                    Toasty.error(context, "Hóa đơn chi tiết đang tồn tại!", Toast.LENGTH_SHORT, true).show();
                    ((HoaDonActivity) context).AdapterCapNhatGiaoDien();
                }
                if (kt == true) {


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
                                    HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                                    hoaDonDAO.deleteHoaDon(listHoaDon.get(position).maHoadon);
                                    Toasty.success(context, "Hoàn tất!", Toast.LENGTH_SHORT, true).show();
                                    ((HoaDonActivity) context).AdapterCapNhatGiaoDien();
                                    // click
                                }
                            })
                            .setNegativeButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    //click
                                    ((HoaDonActivity) context).AdapterCapNhatGiaoDien();
                                    Toasty.success(context, "Đã Hủy", Toast.LENGTH_LONG, true).show();
                                }
                            })
                            .show();
                }
            }
        });

        holder.imgChiTiet.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((HoaDonActivity) context), HoaDonChiTietActivity.class);

                HoaDon hoaDon = listHoaDon.get(position);
                String index = hoaDon.maHoadon;
                intent.putExtra("data", index);
                ((HoaDonActivity) context).startActivity(intent);
                ((HoaDonActivity) context).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Animation matKhauAnim = AnimationUtils.loadAnimation(context, R.anim.anim_icon_swipe_open);
                holder.icon_Swipe.startAnimation(matKhauAnim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
    }

    @Override
    public Filter getFilter() {
        return HoaDonFilter;
    }

    public class Myholder extends RecyclerView.ViewHolder {
        public ImageView imgDelete, imgChiTiet, icon_Swipe;
        public TextView tvMaHoaDon, tvNgayMua;

        public Myholder(View itemView) {
            super(itemView);
            imgChiTiet = itemView.findViewById(R.id.imgChiTiet);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            icon_Swipe = itemView.findViewById(R.id.icon_swipe);
            tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            tvNgayMua = itemView.findViewById(R.id.tvNgayMua);
        }
    }

    //Ham xy ly khi tim kiem
    private Filter HoaDonFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<HoaDon> listHoaDon = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                listHoaDon.addAll(listHoaDonFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (HoaDon item : listHoaDonFull) {
                    if (item.maHoadon.toLowerCase().contains(filterPattern)) {
                        listHoaDon.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listHoaDon;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listHoaDon.clear();
            listHoaDon.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    //Ham xy ly khi tim kiem
}
