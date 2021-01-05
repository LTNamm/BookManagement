package com.example.admin.nienluan3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.admin.lab1_duanmau.R;

public class HelloActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    ImageView logoHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        /*
            Sử lí thời gian xuất hiện giữa 2 activity
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(HelloActivity.this, LoginActivity.class);
                startActivity(i);
                /*
                    sử dụng anim_enter: gọi ảnh ra
                    sử dụng anim_exit : đưa ảnh vào
                 */
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                /*
                    Hàm finish() để đóng màn hình
                 */
                finish();

            }
        }, SPLASH_TIME_OUT);    /*set thời gian 3 giây, sau 3 giây chuyển màn hình*/

        logoHello = findViewById(R.id.logoHello);
        Animation animationZoom = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        logoHello.startAnimation(animationZoom);
    }

}

