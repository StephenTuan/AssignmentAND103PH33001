package com.example.assignment_and103_ph33001;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Ẩn ActionBar nếu bạn đang sử dụng Theme có ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để chuyển sang SignUpActivity
                Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(intent);
                // Đóng SplashActivity để người dùng không thể quay lại
                finish();
            }
        }, SPLASH_DELAY);
    }
}
