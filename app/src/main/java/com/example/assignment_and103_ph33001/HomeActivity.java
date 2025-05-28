package com.example.assignment_and103_ph33001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103_ph33001.Adapter.FruitAdapter;
import com.example.assignment_and103_ph33001.Model.FruitItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FruitAdapter.OnFruitItemClickListener {

    private RecyclerView fruitsRecyclerView;
    private FruitAdapter fruitAdapter;
    private List<FruitItem> fruitItemList;
    private Button categoryAll, categoryVegetables, categoryFruits, categoryMeats;
    ImageView userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fruitsRecyclerView = findViewById(R.id.fruits_recyclerview);
        userAvatar = findViewById(R.id.user_avatar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home_bottom); // Đặt item Home là được chọn

        categoryAll = findViewById(R.id.category_all);
        categoryVegetables = findViewById(R.id.category_vegetables);
        categoryFruits = findViewById(R.id.category_fruits);
        categoryMeats = findViewById(R.id.category_meats);

        setupCategoryButtons();
        setupRecyclerView();
        loadFruitData("all"); // Tải dữ liệu ban đầu cho "All"

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home_bottom) {
                // Đã ở Home rồi
                return true;
            } else if (itemId == R.id.nav_orders_bottom) {
                Toast.makeText(HomeActivity.this, "Chuyển đến Đơn hàng", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(HomeActivity.this, OrdersActivity.class));
                return true;
            } else if (itemId == R.id.nav_cart_bottom) {
                Toast.makeText(HomeActivity.this, "Chuyển đến Giỏ hàng", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(HomeActivity.this, CartActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile_bottom) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class); // Giả sử bạn có HomeActivity
                startActivity(intent);
            }
        });
    }

    private void setupCategoryButtons() {
        View.OnClickListener categoryClickListener = v -> {
            clearCategorySelection();
            v.setSelected(true);
            int id = v.getId();
            if (id == R.id.category_all) {
                loadFruitData("all");
            } else if (id == R.id.category_vegetables) {
                loadFruitData("vegetables");
            } else if (id == R.id.category_fruits) {
                loadFruitData("fruits");
            } else if (id == R.id.category_meats) {
                loadFruitData("meats");
            }
        };

        categoryAll.setOnClickListener(categoryClickListener);
        categoryVegetables.setOnClickListener(categoryClickListener);
        categoryFruits.setOnClickListener(categoryClickListener);
        categoryMeats.setOnClickListener(categoryClickListener);

        // Mặc định chọn "All"
        categoryAll.setSelected(true);
    }

    private void clearCategorySelection() {
        categoryAll.setSelected(false);
        categoryVegetables.setSelected(false);
        categoryFruits.setSelected(false);
        categoryMeats.setSelected(false);
    }


    private void setupRecyclerView() {
        fruitItemList = new ArrayList<>();
        fruitsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fruitAdapter = new FruitAdapter(this, fruitItemList, this);
        fruitsRecyclerView.setAdapter(fruitAdapter);
    }

    private void loadFruitData(String category) {
        fruitItemList.clear(); // Xóa dữ liệu cũ

        // Dữ liệu cứng cho demo
        // Bạn sẽ thay thế phần này bằng việc tải dữ liệu từ API hoặc database
        if (category.equals("all") || category.equals("fruits")) {
            fruitItemList.add(new FruitItem(getString(R.string.sample_fruit_name_1), "14.295", "500 g", R.drawable.orange)); // Cần tạo img_orange_placeholder
            fruitItemList.add(new FruitItem(getString(R.string.sample_fruit_name_2), "20.500", "250 g", R.drawable.strawberry)); // Cần tạo img_strawberry_placeholder
            fruitItemList.add(new FruitItem("Táo Fuji", "18.000", "400 g", R.drawable.harvest));
            fruitItemList.add(new FruitItem("Chuối", "10.000", "1 kg", R.drawable.harvest));
        }
        if (category.equals("all") || category.equals("vegetables")) {
            fruitItemList.add(new FruitItem("Cà chua", "8.000", "300 g", R.drawable.harvest));
            fruitItemList.add(new FruitItem("Dưa chuột", "7.500", "350 g", R.drawable.harvest));
        }
        if (category.equals("all") || category.equals("meats")) {
            fruitItemList.add(new FruitItem("Thịt bò", "150.000", "500 g", R.drawable.harvest));
        }


        if (fruitItemList.isEmpty()) {
            Toast.makeText(this, "Không có sản phẩm cho danh mục " + category, Toast.LENGTH_SHORT).show();
        }
        fruitAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFruitItemClick(FruitItem item) {
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra("FRUIT_NAME", item.getName());
        intent.putExtra("FRUIT_PRICE", item.getPrice());
        intent.putExtra("FRUIT_WEIGHT", item.getWeight());
        intent.putExtra("FRUIT_IMAGE_RES_ID", item.getImageResourceId());
        // Bạn có thể truyền thêm các thông tin khác nếu cần
        startActivity(intent);
    }


}