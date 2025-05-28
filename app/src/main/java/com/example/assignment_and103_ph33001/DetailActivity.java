package com.example.assignment_and103_ph33001;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgFruit;
    private TextView tvFruitName, tvPrice, tvWeight, tvQuantity;
    private Button btnMinus, btnPlus, btnAddToCart;
    private ImageButton btnBack;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ánh xạ view
        imgFruit = findViewById(R.id.img_fruit);
        tvFruitName = findViewById(R.id.tv_fruit_name);
        tvPrice = findViewById(R.id.tv_price);
        tvWeight = findViewById(R.id.tv_weight);
        tvQuantity = findViewById(R.id.tv_quantity);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnBack = findViewById(R.id.btn_back);

        // Nhận dữ liệu từ Intent
        String fruitName = getIntent().getStringExtra("FRUIT_NAME");
        String price = getIntent().getStringExtra("FRUIT_PRICE");
        String weight = getIntent().getStringExtra("FRUIT_WEIGHT");
        int imageResId = getIntent().getIntExtra("FRUIT_IMAGE_RES_ID", 0);

        // Hiển thị dữ liệu
        tvFruitName.setText(fruitName);
        tvPrice.setText(price);
        tvWeight.setText(weight);
        if (imageResId != 0) {
            imgFruit.setImageResource(imageResId);
        }

        // Xử lý sự kiện
        btnBack.setOnClickListener(v -> finish());

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        btnPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        });

        btnAddToCart.setOnClickListener(v -> {
            String message = String.format("Đã thêm %d %s vào giỏ hàng", quantity, fruitName);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
}