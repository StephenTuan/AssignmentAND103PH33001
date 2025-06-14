package com.example.assignment_and103_ph33001;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103_ph33001.Adapter.FruitAdapter;
import com.example.assignment_and103_ph33001.Adapter.CategoryAdapter;
import com.example.assignment_and103_ph33001.Model.FruitItem;
import com.example.assignment_and103_ph33001.Model.CateItem;
import com.example.assignment_and103_ph33001.Model.Response;
import com.example.assignment_and103_ph33001.handle.Item_Fruit_handle;
import com.example.assignment_and103_ph33001.services.HttpRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity{

    private RecyclerView fruitsRecyclerView;
    private FruitAdapter fruitAdapter;
    private List<FruitItem> fruitItemList;
    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CateItem> categoryList;
    ImageView addFruitBtn;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpRequest = new HttpRequest();

        fruitsRecyclerView = findViewById(R.id.fruits_recyclerview);
        categoriesRecyclerView = findViewById(R.id.categories_recyclerview);
        addFruitBtn = findViewById(R.id.btn_plus);
        
        // Kiểm tra xem nút có được tìm thấy không
        if (addFruitBtn == null) {
            Log.e("HomeActivity", "addFruitBtn is null - không tìm thấy R.id.btn_plus");
        } else {
            Log.d("HomeActivity", "addFruitBtn found successfully");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home_bottom); // Đặt item Home là được chọn

        setupRecyclerView();
        setupCategoryRecyclerView();
        fetchCategories();
        fetchFruitList(); // Tải dữ liệu ban đầu cho "All"
        
        // Setup click listener cho nút add fruit
        if (addFruitBtn != null) {
            addFruitBtn.setOnClickListener(v -> {
                Log.d("HomeActivity", "Add button clicked!");
                showAddDialog();
            });
        }

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
    }





    private void setupRecyclerView() {
        fruitItemList = new ArrayList<>();
        fruitsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fruitAdapter = new FruitAdapter(this, (ArrayList<FruitItem>) fruitItemList, new Item_Fruit_handle() {
            @Override
            public void onEdit(int position) {
                Log.d("apiSua", "vi trí: " + position);
                showDialog(fruitItemList.get(position), position);
            }
            @Override
            public void onDelete(int position) {
                Log.d("apiXoa", "vị trí: " + position);
                deleteFruit(fruitItemList.get(position).getId(), position);
            }
        });
        fruitsRecyclerView.setAdapter(fruitAdapter);
    }

    private List<FruitItem> allFruitItems = new ArrayList<>(); // Lưu tất cả dữ liệu

    private void fetchFruitList() {
        // Gọi API bằng HttpRequest
        Call<Response<ArrayList<FruitItem>>> call = httpRequest.callApi().getListFruist();
        call.enqueue(new Callback<Response<ArrayList<FruitItem>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<FruitItem>>> call,
                                   retrofit2.Response<Response<ArrayList<FruitItem>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<ArrayList<FruitItem>> apiResponse = response.body();

                    Log.d("API_RESPONSE", "dữ liệu: " + apiResponse.getData().toString());
                    if (apiResponse.getStatus() == 200) {
                        allFruitItems.clear();
                        allFruitItems.addAll(apiResponse.getData());
                        
                        fruitItemList.clear();
                        fruitItemList.addAll(apiResponse.getData());
                        fruitAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HomeActivity.this, "Lỗi: " + apiResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<FruitItem>>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFruitData(String category) {
        fruitItemList.clear();
        
        if (category.equals("all")) {
            fruitItemList.addAll(allFruitItems);
        } else {
            for (FruitItem item : allFruitItems) {
                String categoryName = getCategoryName(item.getId_category());
                if (categoryName != null && categoryName.toLowerCase().contains(category.toLowerCase())) {
                    fruitItemList.add(item);
                }
            }
        }
        
        fruitAdapter.notifyDataSetChanged();
        
        // Log để debug
        Log.d("CATEGORY_FILTER", "Category: " + category + ", Items found: " + fruitItemList.size());
    }
    
    private String getCategoryName(Object categoryObj) {
        if (categoryObj == null) return null;
        
        // Nếu là String trực tiếp
        if (categoryObj instanceof String) {
            return (String) categoryObj;
        }
        
        // Nếu là object có thuộc tính name
        try {
            if (categoryObj instanceof com.google.gson.internal.LinkedTreeMap) {
                com.google.gson.internal.LinkedTreeMap<String, Object> categoryMap = 
                    (com.google.gson.internal.LinkedTreeMap<String, Object>) categoryObj;
                Object name = categoryMap.get("name");
                return name != null ? name.toString() : null;
            }
        } catch (Exception e) {
            Log.e("CATEGORY_PARSE", "Error parsing category: " + e.getMessage());
        }
        
        return categoryObj.toString();
    }

    private String getCategoryId(Object categoryObj) {
        if (categoryObj == null) return "";
        
        // Nếu là String trực tiếp
        if (categoryObj instanceof String) {
            return (String) categoryObj;
        }
        
        // Nếu là object có thuộc tính _id
        try {
            if (categoryObj instanceof com.google.gson.internal.LinkedTreeMap) {
                com.google.gson.internal.LinkedTreeMap<String, Object> categoryMap = 
                    (com.google.gson.internal.LinkedTreeMap<String, Object>) categoryObj;
                Object id = categoryMap.get("_id");
                return id != null ? id.toString() : "";
            }
        } catch (Exception e) {
            Log.e("CATEGORY_ID_PARSE", "Error parsing category ID: " + e.getMessage());
        }
        
        // Fallback: thử extract từ toString()
        try {
            String categoryStr = categoryObj.toString();
            if (categoryStr.contains("_id=")) {
                int startIndex = categoryStr.indexOf("_id=") + 4;
                int endIndex = categoryStr.indexOf(",", startIndex);
                if (endIndex == -1) endIndex = categoryStr.indexOf("}", startIndex);
                return categoryStr.substring(startIndex, endIndex).trim();
            }
        } catch (Exception e) {
            Log.e("CATEGORY_ID_FALLBACK", "Error in fallback parsing: " + e.getMessage());
        }
        
        return "";
    }

    private void showDialog(FruitItem fruit, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_fruit, null);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText etNameFruist = dialogView.findViewById(R.id.etNameFruist);
        EditText etId_category = dialogView.findViewById(R.id.etId_category);
        EditText etDistributorFruist = dialogView.findViewById(R.id.etDistributorFruist);
        EditText etPriceFruist = dialogView.findViewById(R.id.edPriceFruist);
        EditText etDescriptionFruist = dialogView.findViewById(R.id.edDescriptionFruist);
        EditText etRateFruist = dialogView.findViewById(R.id.edRateFruist);
        EditText etImageFruist = dialogView.findViewById(R.id.etImageFruist);

        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Điền dữ liệu hiện tại vào dialog
        if (fruit != null) {
            etNameFruist.setText(fruit.getNameFruist() != null ? fruit.getNameFruist() : "");
            etDistributorFruist.setText(fruit.getDistributorFruist() != null ? fruit.getDistributorFruist() : "");
            etPriceFruist.setText(fruit.getPriceFruist() != null ? fruit.getPriceFruist() : "");
            etDescriptionFruist.setText(fruit.getDescriptionFruist() != null ? fruit.getDescriptionFruist() : "");
            etRateFruist.setText(fruit.getRateFruist() != null ? fruit.getRateFruist() : "");
            etImageFruist.setText(fruit.getImageFruist() != null ? fruit.getImageFruist() : "");
            // Xử lý category ID - chỉ lấy ID
            String categoryId = getCategoryId(fruit.getId_category());
            etId_category.setText(categoryId);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String name = etNameFruist.getText().toString().trim();
            String categoryId = etId_category.getText().toString().trim();
            String distributor = etDistributorFruist.getText().toString().trim();
            String price = etPriceFruist.getText().toString().trim();
            String description = etDescriptionFruist.getText().toString().trim();
            String rate = etRateFruist.getText().toString().trim();
            String imageUrl = etImageFruist.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên và giá sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng FruitItem để update
            FruitItem updatedFruit = new FruitItem();
            updatedFruit.setNameFruist(name);
            updatedFruit.setId_category(categoryId);
            updatedFruit.setDistributorFruist(distributor);
            updatedFruit.setPriceFruist(price);
            updatedFruit.setDescriptionFruist(description);
            updatedFruit.setRateFruist(rate);
            updatedFruit.setImageFruist(imageUrl); // Giữ nguyên ảnh cũ

            updateFruit(fruit.getId(), updatedFruit, position);
            dialog.dismiss();
        });

        dialog.show();
        Log.d("Dialog", "Dialog edit đã tạo");
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_fruit, null);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText etNameFruist = dialogView.findViewById(R.id.etNameFruist);
        EditText etId_category = dialogView.findViewById(R.id.etId_category);
        EditText etDistributorFruist = dialogView.findViewById(R.id.etDistributorFruist);
        EditText etPriceFruist = dialogView.findViewById(R.id.edPriceFruist);
        EditText etDescriptionFruist = dialogView.findViewById(R.id.edDescriptionFruist);
        EditText etRateFruist = dialogView.findViewById(R.id.edRateFruist);
        EditText etImageFruist = dialogView.findViewById(R.id.etImageFruist);

        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Không điền dữ liệu sẵn cho dialog add
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String name = etNameFruist.getText().toString().trim();
            String categoryId = etId_category.getText().toString().trim();
            String distributor = etDistributorFruist.getText().toString().trim();
            String price = etPriceFruist.getText().toString().trim();
            String description = etDescriptionFruist.getText().toString().trim();
            String rate = etRateFruist.getText().toString().trim();
            String imageUrl = etImageFruist.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên và giá sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng FruitItem mới
            FruitItem newFruit = new FruitItem();
            newFruit.setNameFruist(name);
            newFruit.setId_category(categoryId);
            newFruit.setDistributorFruist(distributor);
            newFruit.setPriceFruist(price);
            newFruit.setDescriptionFruist(description);
            newFruit.setRateFruist(rate);
            newFruit.setImageFruist(imageUrl); // Lấy URL ảnh từ EditText

            addFruit(newFruit);
            dialog.dismiss();
        });

        dialog.show();
        Log.d("Dialog", "Dialog add đã tạo");
    }

    private void addFruit(FruitItem newFruit) {
        Call<Response<FruitItem>> call = httpRequest.callApi().addFruist(newFruit);
        call.enqueue(new Callback<Response<FruitItem>>() {
            @Override
            public void onResponse(Call<Response<FruitItem>> call, retrofit2.Response<Response<FruitItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<FruitItem> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        // Thêm item mới vào đầu danh sách
                        fruitItemList.add(0, apiResponse.getData());
                        if (allFruitItems != null) {
                            allFruitItems.add(0, apiResponse.getData());
                        }
                        fruitAdapter.notifyItemInserted(0);
                        fruitsRecyclerView.scrollToPosition(0); // Scroll về đầu để thấy item mới
                        Toast.makeText(HomeActivity.this, "Thêm fruit thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HomeActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Lỗi thêm fruit thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<FruitItem>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void addUser(User user){
//        Call<Response<User>> call = httpRequest.callApi().addUser(user);
//        call.enqueue(new Callback<Response<User>>() {
//            @Override
//            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Response<User> apiResponse = response.body();
//                    if (apiResponse.getStatus() == 200) {
//                        fetchUserList();
//                        Toast.makeText(MainActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(HomeActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Lỗi thêm user", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<Response<User>> call, Throwable t) {
//
//            }
//        });
//    }
//    private void updateUser(User user, int position) {
//        Call<Response<User>> call = httpRequest.callApi().updateUser(user.getId(), user);
//        call.enqueue(new Callback<Response<User>>() {
//            @Override
//            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Response<User> apiResponse = response.body();
//                    Log.d("apiEdit", "dữ liệu: "+apiResponse.toString());
//                    if (apiResponse.getStatus() == 200) {
//                        fetchUserList();
//                        Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(MainActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Lỗi cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response<User>> call, Throwable t) {
//                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    private void updateFruit(String id, FruitItem updatedFruit, int position) {
        Call<Response<FruitItem>> call = httpRequest.callApi().updateFruist(id, updatedFruit);
        call.enqueue(new Callback<Response<FruitItem>>() {
            @Override
            public void onResponse(Call<Response<FruitItem>> call, retrofit2.Response<Response<FruitItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<FruitItem> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        // Cập nhật item trong list
                        fruitItemList.set(position, apiResponse.getData());
                        if (allFruitItems != null) {
                            // Tìm và cập nhật trong allFruitItems
                            for (int i = 0; i < allFruitItems.size(); i++) {
                                if (allFruitItems.get(i).getId().equals(id)) {
                                    allFruitItems.set(i, apiResponse.getData());
                                    break;
                                }
                            }
                        }
                        fruitAdapter.notifyItemChanged(position);
                        Toast.makeText(HomeActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HomeActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Lỗi cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<FruitItem>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteFruit(String id, int position) {
        Call<Response<Void>> call = httpRequest.callApi().deleteFruist(id);
        call.enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, retrofit2.Response<Response<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<Void> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        fetchFruitList();
                        Toast.makeText(HomeActivity.this, "xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HomeActivity.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "lỗi xóa dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//    @Override
//    public void onFruitItemClick(FruitItem item) {
//        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
//        intent.putExtra("FRUIT_NAME", item.getName());
//        intent.putExtra("FRUIT_PRICE", item.getPrice());
//        intent.putExtra("FRUIT_WEIGHT", item.getWeight());
//        intent.putExtra("FRUIT_IMAGE_RES_ID", item.getImageResourceId());
//        // Bạn có thể truyền thêm các thông tin khác nếu cần
//        startActivity(intent);
//    }

    private void setupCategoryRecyclerView() {
        categoryList = new ArrayList<>();
        androidx.recyclerview.widget.LinearLayoutManager layoutManager = 
            new androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false);
        categoriesRecyclerView.setLayoutManager(layoutManager);
        
        categoryAdapter = new CategoryAdapter(categoryList, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(CateItem category, int position) {
                categoryAdapter.setSelectedPosition(position);
                if (category.getNameCate().equals("All")) {
                    loadFruitData("all");
                } else {
                    fetchFruitsByCategory(category.getId_cate());
                }
            }
        });
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    private void fetchCategories() {
        Call<Response<ArrayList<CateItem>>> call = httpRequest.callApi().getListCategories();
        call.enqueue(new Callback<Response<ArrayList<CateItem>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<CateItem>>> call, 
                                 retrofit2.Response<Response<ArrayList<CateItem>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<ArrayList<CateItem>> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        categoryList.clear();
                        // Thêm category "All" đầu tiên
                        categoryList.add(new CateItem("All", "all"));
                        // Thêm các category từ API
                        categoryList.addAll(apiResponse.getData());
                        categoryAdapter.notifyDataSetChanged();
                        // Mặc định chọn "All"
                        categoryAdapter.setSelectedPosition(0);
                    } else {
                        Toast.makeText(HomeActivity.this, "Lỗi: " + apiResponse.getMessage(), 
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Lỗi tải danh sách category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<CateItem>>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFruitsByCategory(String categoryId) {
        Call<Response<ArrayList<FruitItem>>> call = httpRequest.callApi().getFruitsByCategory(categoryId);
        call.enqueue(new Callback<Response<ArrayList<FruitItem>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<FruitItem>>> call, 
                                 retrofit2.Response<Response<ArrayList<FruitItem>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response<ArrayList<FruitItem>> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        fruitItemList.clear();
                        fruitItemList.addAll(apiResponse.getData());
                        fruitAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HomeActivity.this, "Lỗi: " + apiResponse.getMessage(), 
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Lỗi tải sản phẩm theo category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<FruitItem>>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}