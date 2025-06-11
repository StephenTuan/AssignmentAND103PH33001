package com.example.assignment_and103_ph33001.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private Context context;
    private ArrayList<User> ds;
    private Item_User_handle handle;

    public UserAdapter(Context context, ArrayList<User> ds, Item_User_handle handle) {
        this.context = context;
        this.ds = ds;
        this.handle = handle;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = ds.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.tvAge.setText(String.valueOf(user.getAge()));

        holder.btnEdit.setOnClickListener(v -> handle.onEdit(position));
        holder.btnDelete.setOnClickListener(v -> handle.onDelete(position));

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvAge;
        Button btnEdit, btnDelete;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAge = itemView.findViewById(R.id.tvAge);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById((R.id.btnDelete));
        }
    }
}
