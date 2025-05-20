package com.example.assignment_and103_ph33001.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103_ph33001.Model.FruitItem;
import com.example.assignment_and103_ph33001.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> {

    private List<FruitItem> fruitList;
    private Context context;

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

        holder.fruitName.setText(currentItem.getName());
        holder.fruitPrice.setText(currentItem.getPrice() + " " + context.getString(R.string.rupees_symbol));
        holder.fruitWeight.setText(currentItem.getWeight());
        holder.fruitImage.setImageResource(currentItem.getImageResourceId());

        // Cập nhật icon yêu thích
        if (currentItem.isFavorite()) {
            holder.favoriteIcon.setImageResource(R.drawable.heartfill); // Cần tạo icon này
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.heart);
        }

        holder.favoriteIcon.setOnClickListener(v -> {
            currentItem.setFavorite(!currentItem.isFavorite());
            notifyItemChanged(position); // Cập nhật lại item để thay đổi icon
            String message = currentItem.isFavorite() ? "Đã thêm vào yêu thích" : "Đã xóa khỏi yêu thích";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        });

        holder.addToCartButton.setOnClickListener(v -> {
            Toast.makeText(context, currentItem.getName() + " đã được thêm vào giỏ", Toast.LENGTH_SHORT).show();
            // Xử lý logic thêm vào giỏ hàng ở đây
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFruitItemClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    static class FruitViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        ImageView favoriteIcon;
        TextView fruitName;
        TextView fruitWeight;
        TextView fruitPrice;
        ImageButton addToCartButton;

        public FruitViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = itemView.findViewById(R.id.fruit_image);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
            fruitName = itemView.findViewById(R.id.fruit_name);
            fruitWeight = itemView.findViewById(R.id.fruit_weight);
            fruitPrice = itemView.findViewById(R.id.fruit_price);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }
    }
}
