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
import com.example.admin.nienluan3.Model.TheLoai;
import com.example.admin.lab1_duanmau.R;
import com.example.admin.nienluan3.DAO.TheLoaiDAO;
import com.example.admin.nienluan3.TheLoaiActivity;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AdapterTheLoai extends RecyclerView.Adapter<AdapterTheLoai.MyHolder> {

    Context context;
    ArrayList<TheLoai> listTheLoai = new ArrayList<TheLoai>();

    public AdapterTheLoai(Context context, ArrayList<TheLoai> listTheLoai) {
        this.context = context;
        this.listTheLoai = listTheLoai;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_item_the_loai, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        TheLoai theLoai = listTheLoai.get(position);

        holder.tvTenTheLoai.setText(theLoai.tenTheLoai);
        holder.tvMaTheLoai.setText(theLoai.maTheLoai);

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
                                TheLoaiDAO theLoaiDAO = new TheLoaiDAO(context);
                                theLoaiDAO.deleteTheLoai(listTheLoai.get(position).maTheLoai);
                                ((TheLoaiActivity) context).AdapterCapNhatGiaoDien();
                                Toasty.success(context, "Đã Xóa!", Toast.LENGTH_LONG,true).show();
                                // click
                            }
                        })
                        .setNegativeButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                //click
                                ((TheLoaiActivity) context).AdapterCapNhatGiaoDien();
                                Toasty.success(context, "Đã Hủy!", Toast.LENGTH_LONG,true).show();
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
                holder.icon_swipe.startAnimation(matKhauAnim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTheLoai.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView tvMaTheLoai, tvTenTheLoai;
        public ImageView imgEdit, imgDelete, icon_swipe;

        public MyHolder(View itemView) {
            super(itemView);
            tvMaTheLoai = itemView.findViewById(R.id.tvMaTheLoai);
            tvTenTheLoai = itemView.findViewById(R.id.tvTenTheLoai);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            icon_swipe = itemView.findViewById(R.id.icon_swipe);


        }
    }

    private void evenEdit(final int position) {
        //dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inf = ((TheLoaiActivity) context).getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_for_update_loai, null);

        final EditText edtMaTheLoai = view1.findViewById(R.id.edtMaTheLoai);
        final EditText edtTenTheLoai = view1.findViewById(R.id.edtTenTheLoai);
        final EditText edtViTri = view1.findViewById(R.id.edtViTri);
        final EditText edtMoTa = view1.findViewById(R.id.edtMoTa);

        TheLoai theLoai = listTheLoai.get(position);
        edtMaTheLoai.setText(theLoai.maTheLoai);
        edtTenTheLoai.setText(theLoai.tenTheLoai);
        edtViTri.setText(theLoai.viTri + "");
        edtMoTa.setText(theLoai.moTa);

        alertDialog.setView(view1);

        alertDialog.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String maTheLoai = edtMaTheLoai.getText().toString().trim();
                String tenTheLoai = edtTenTheLoai.getText().toString().trim();
                int viTri = 0;
                try {
                    viTri = Integer.parseInt(edtViTri.getText().toString().trim());
                } catch (Exception e) {
                }
                String moTa = edtMoTa.getText().toString().trim();

                if (maTheLoai.equals("") || tenTheLoai.equals("") || viTri <= 0 || moTa.equals("")) {
                    Toasty.error(context, "Lỗi!", Toast.LENGTH_SHORT, true).show();
                    evenEdit(position);
                } else {

                    TheLoaiDAO theLoaiDAO = new TheLoaiDAO(context);
                    TheLoai theLoai1 = new TheLoai(listTheLoai.get(position).maTheLoai, tenTheLoai, moTa, viTri);
                    theLoaiDAO.updateTheLoai(theLoai1);

                    Toasty.success(context, "Hoàn tất!", Toast.LENGTH_SHORT, true).show();
                    ((TheLoaiActivity) context).AdapterCapNhatGiaoDien();
                }

            }
        });
        alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toasty.success(context, "Đã hủy", Toast.LENGTH_SHORT, true).show();
                ((TheLoaiActivity) context).AdapterCapNhatGiaoDien();
            }
        });
        alertDialog.show();
        //dialog
    }
}
