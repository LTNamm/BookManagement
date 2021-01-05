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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.example.admin.nienluan3.Model.NguoiDung;
import com.example.admin.nienluan3.NguoidungActivity;
import com.example.admin.lab1_duanmau.R;
import com.example.admin.nienluan3.DAO.NguoiDungDAO;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AdapterNguoiDung extends RecyclerView.Adapter<AdapterNguoiDung.MyHolder> {

    Context context;
    ArrayList<NguoiDung> listNguoiDung = new ArrayList<NguoiDung>();

    public AdapterNguoiDung(Context context, ArrayList<NguoiDung> listNguoiDung) {
        this.context = context;
        this.listNguoiDung = listNguoiDung;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_item_nguoidung, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        final NguoiDung nguoiDung = listNguoiDung.get(position);
        holder.tvHoVaTen.setText(nguoiDung.hoVaTen);
        holder.tvSoDienThoai.setText(nguoiDung.phone);

        //random inserImgs

        if (position % 8 == 0) {
            holder.imgNguoiDung.setImageResource(R.drawable.emone);
        } else if (position % 8 == 1) {
            holder.imgNguoiDung.setImageResource(R.drawable.emtwo);
        } else if (position % 8 == 2) {
            holder.imgNguoiDung.setImageResource(R.drawable.emthree);
        } else if (position % 8 == 3) {
            holder.imgNguoiDung.setImageResource(R.drawable.user_2);
        } else if (position % 8 == 4) {
            holder.imgNguoiDung.setImageResource(R.drawable.user_3);
        } else if (position % 8 == 5) {
            holder.imgNguoiDung.setImageResource(R.drawable.user_4);
        } else if (position % 8 == 6) {
            holder.imgNguoiDung.setImageResource(R.drawable.user_5);
        } else {
            holder.imgNguoiDung.setImageResource(R.drawable.user_6);
        }
        //random InserImgs

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
                                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(context);
                                nguoiDungDAO.deleteNguoiDung(listNguoiDung.get(position).userName);
                                ((NguoidungActivity) context).AdapterCapNhatGiaoDien();
                                Toasty.success(context, "Hoàn tất!", Toast.LENGTH_LONG, true).show();
                                // click
                            }
                        })
                        .setNegativeButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                //click
                                ((NguoidungActivity) context).AdapterCapNhatGiaoDien();
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
        return listNguoiDung.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView imgNguoiDung, imgDelete, imgEdit, icon_Swipe;
        public TextView tvHoVaTen, tvSoDienThoai;

        public MyHolder(View itemView) {
            super(itemView);
            imgNguoiDung = itemView.findViewById(R.id.imgNguoiDung);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            tvHoVaTen = itemView.findViewById(R.id.tvHoVaTen);
            tvSoDienThoai = itemView.findViewById(R.id.tvSoDienThoai);
            icon_Swipe = itemView.findViewById(R.id.icon_swipe);
        }
    }

    private void evenEdit(final int position) {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inf = ((NguoidungActivity) context).getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_update_nguoidung, null);
        final EditText edtSoDientThoai = view1.findViewById(R.id.edtSoDienThoaiEdit);
        final EditText edtHoVaTen = view1.findViewById(R.id.edtHoVaTenEdit);

        final NguoiDung nguoiDung = listNguoiDung.get(position);
        edtHoVaTen.setText(nguoiDung.hoVaTen);
        edtSoDientThoai.setText(nguoiDung.phone);

        alertDialog.setView(view1);

        alertDialog.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String HoVaTen = edtHoVaTen.getText().toString().trim();
                String SoDienThoai = edtSoDientThoai.getText().toString().trim();

                if (SoDienThoai.equals("") || HoVaTen.equals("")) {
                    evenEdit(position);
                    edtHoVaTen.setText(HoVaTen);
                    edtSoDientThoai.setText(SoDienThoai);
                    Toasty.error(context, "Lỗi!", Toast.LENGTH_SHORT, true).show();
                } else {
                    String index = nguoiDung.userName;
                    NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(context);
                    NguoiDung nguoiDung = new NguoiDung(index, SoDienThoai, HoVaTen);

                    nguoiDungDAO.updateThongTinNguoiDung(nguoiDung);
                    ((NguoidungActivity) context).AdapterCapNhatGiaoDien();
                    Toasty.success(context, "Hoàn tất!", Toast.LENGTH_SHORT, true).show();

                }
            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((NguoidungActivity) context).AdapterCapNhatGiaoDien();
                Toasty.success(((NguoidungActivity) context), "Đã hủy!", Toast.LENGTH_SHORT, true).show();
            }
        });
        alertDialog.show();
        //dialog
    }
}
