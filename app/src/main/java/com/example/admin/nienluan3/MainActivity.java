package com.example.admin.nienluan3;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.admin.lab1_duanmau.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imgNguoiDung, imgTheLoai, imgSach, imgHoaDon;
    private CardView cardOne, cardTwo, cardThree, cardFour;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        animationCardView();

        imgNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NguoidungActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);

            }
        });

        imgTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TheLoaiActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });

        imgSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SachActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });

        imgHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HoaDonActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }

    private void AnhXa() {
        imgNguoiDung = findViewById(R.id.imgNguoiDung);
        imgTheLoai = findViewById(R.id.imgTheLoai);
        imgSach = findViewById(R.id.imgSach);
        imgHoaDon = findViewById(R.id.imgHoaDon);

        cardOne = findViewById(R.id.cardOne);
        cardTwo = findViewById(R.id.cardTwo);
        cardThree = findViewById(R.id.cardThree);
        cardFour = findViewById(R.id.cardFour);

    }

    private void animationCardView() {
        Animation one = AnimationUtils.loadAnimation(this, R.anim.anim_card_one);
        cardOne.startAnimation(one);

        Animation two = AnimationUtils.loadAnimation(this, R.anim.anim_card_two);
        cardTwo.startAnimation(two);

        Animation three = AnimationUtils.loadAnimation(this, R.anim.anim_card_three);
        cardThree.startAnimation(three);

        Animation four = AnimationUtils.loadAnimation(this, R.anim.anim_card_four);
        cardFour.startAnimation(four);
    }
}
