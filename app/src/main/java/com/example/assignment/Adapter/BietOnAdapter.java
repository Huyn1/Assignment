package com.example.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Model.DieuBietOn;
import com.example.assignment.R;

import java.util.List;

public class BietOnAdapter extends RecyclerView.Adapter<BietOnAdapter.BietOnViewHoder> {
    private Context context;
    private List<DieuBietOn> data;

    public BietOnAdapter(Context context, List<DieuBietOn> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public BietOnViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bieton, parent, false);
        return new BietOnViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BietOnViewHoder holder, int position) {
        DieuBietOn dieuBietOn = data.get(position);
        holder.dateTextView.setText(dieuBietOn.getDate());
        holder.dieu1TextView.setText(dieuBietOn.getDieu1());
        holder.dieu2TextView.setText(dieuBietOn.getDieu2());
        holder.dieu3TextView.setText(dieuBietOn.getDieu3());
        holder.dieu4TextView.setText(dieuBietOn.getDieu4());
        holder.dieu5TextView.setText(dieuBietOn.getDieu5());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class BietOnViewHoder extends RecyclerView.ViewHolder {
        TextView dateTextView, dieu1TextView, dieu2TextView, dieu3TextView, dieu4TextView, dieu5TextView;

        public BietOnViewHoder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.txt_ngayviet);
            dieu1TextView = itemView.findViewById(R.id.txt_dieu1);
            dieu2TextView = itemView.findViewById(R.id.txt_dieu2);
            dieu3TextView = itemView.findViewById(R.id.txt_dieu3);
            dieu4TextView = itemView.findViewById(R.id.txt_dieu4);
            dieu5TextView = itemView.findViewById(R.id.txt_dieu5);
        }
    }
}
