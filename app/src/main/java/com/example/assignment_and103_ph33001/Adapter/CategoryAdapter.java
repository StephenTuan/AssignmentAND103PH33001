package com.example.assignment_and103_ph33001.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103_ph33001.Model.CateItem;
import com.example.assignment_and103_ph33001.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CateItem> categoryList;
    private OnCategoryClickListener listener;
    private int selectedPosition = 0; // Default "All" selected

    public interface OnCategoryClickListener {
        void onCategoryClick(CateItem category, int position);
    }

    public CategoryAdapter(List<CateItem> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousPosition);
        notifyItemChanged(selectedPosition);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CateItem category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getNameCate());
        
        // Set selected state for both TextView and itemView
        boolean isSelected = position == selectedPosition;
        holder.tvCategoryName.setSelected(isSelected);
        holder.itemView.setSelected(isSelected);
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                setSelectedPosition(position);
                listener.onCategoryClick(category, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}