package com.project.sportycart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.sportycart.R;
import com.project.sportycart.entity.UserLog;

import java.util.List;

public class LoginLogAdapter extends RecyclerView.Adapter<LoginLogAdapter.MyViewHolder> {
    static List<UserLog> list;
    public LoginLogAdapter(List<UserLog> List){
        this.list = List;
    }
    @NonNull
    @Override
    public LoginLogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.login_recycle_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoginLogAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.timeStamp);
        }
    }
}
