package com.example.assignment_and103_ph33001.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103_ph33001.Model.FruitItem;
import com.example.assignment_and103_ph33001.R;
import com.example.assignment_and103_ph33001.handle.Item_User_handle;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> {

    private List<FruitItem> fruitList;
    private Context context;

    private Item_User_handle handle;

    public interface OnFruitItemClickListener {
        void onFruitItemClick(FruitItem item);
    }
    private OnFruitItemClickListener listener;


    public FruitAdapter(Context context, List<FruitItem> fruitList, OnFruitItemClickListener listener) {
        this.context = context;
        this.fruitList = fruitList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fruit, parent, false);
        return new FruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitViewHolder holder, int position) {
        FruitItem currentItem = fruitList.get(position);

        holder.nameFruist.setText(currentItem.getNameFruist());
        holder.priceFruist.setText(currentItem.getPriceFruist());
        holder.rateFruist.setText(currentItem.getRateFruist());

        // Cập nhật icon yêu thích
//        if (currentItem.isFavorite()) {
//            holder.favoriteIcon.setImageResource(R.drawable.heartfill); // Cần tạo icon này
//        } else {
//            holder.favoriteIcon.setImageResource(R.drawable.heart);
//        }
//
//        holder.favoriteIcon.setOnClickListener(v -> {
//            currentItem.setFavorite(!currentItem.isFavorite());
//            notifyItemChanged(position); // Cập nhật lại item để thay đổi icon
//            String message = currentItem.isFavorite() ? "Đã thêm vào yêu thích" : "Đã xóa khỏi yêu thích";
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//        });

        holder.addToCartButton.setOnClickListener(v -> {
            Toast.makeText(context, currentItem.getNameFruist() + " đã được thêm vào giỏ", Toast.LENGTH_SHORT).show();
            // Xử lý logic thêm vào giỏ hàng ở đây
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFruitItemClick(currentItem);
            }
        });

        holder.btnEdit.setOnClickListener(v -> handle.onEdit(position));
        holder.btnDelete.setOnClickListener(v -> handle.onDelete(position));
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    static class FruitViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
//        ImageView favoriteIcon;
        TextView nameFruist, id_category, distributorFruist, priceFruist, descriptionFruist, rateFruist;
        ImageButton addToCartButton, btnEdit, btnDelete;

        public FruitViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = itemView.findViewById(R.id.fruit_image);
//            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
            nameFruist = itemView.findViewById(R.id.fruit_name);
            priceFruist = itemView.findViewById(R.id.fruit_price);
            rateFruist = itemView.findViewById(R.id.fruit_rate);
            btnEdit = itemView.findViewById(R.id.edit_button);
            btnDelete = itemView.findViewById((R.id.delete_button));
        }
    }
}
