package com.example.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Model.HoatDOng;
import com.example.assignment.R;

import java.util.List;

public class HoatDongAdapter extends RecyclerView.Adapter<HoatDongAdapter.HoatDongViewHolder> {
    private Context context;
    private List<HoatDOng> listHoatDong;

    public HoatDongAdapter(Context context, List<HoatDOng> listHoatDong) {
        this.context = context;
        this.listHoatDong = listHoatDong;
    }

    @NonNull
    @Override
    public HoatDongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chaybo, parent, false);
        return new HoatDongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoatDongViewHolder holder, int position) {
        HoatDOng hoatDOng = listHoatDong.get(position);
        holder.txtNgayHoatDong.setText(hoatDOng.getNgay());
        holder.txtSoBuoc.setText(String.valueOf(hoatDOng.getSoBuoc()));
        holder.txtCalo.setText(String.valueOf(hoatDOng.getSoCalo()));
        holder.txtTime.setText(String.valueOf(hoatDOng.getThoiGian()));
    }

    @Override
    public int getItemCount() {
        return listHoatDong.size();
    }

    public static class HoatDongViewHolder extends RecyclerView.ViewHolder {
        TextView txtNgayHoatDong, txtSoBuoc, txtCalo, txtTime;

        public HoatDongViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNgayHoatDong = itemView.findViewById(R.id.txt_ngaychay);
            txtSoBuoc = itemView.findViewById(R.id.txt_buocchan_chay);
            txtCalo = itemView.findViewById(R.id.txt_calo_chay);
            txtTime = itemView.findViewById(R.id.txt_time_chay);
        }
    }
}
