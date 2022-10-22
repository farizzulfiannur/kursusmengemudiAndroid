package com.example.kursusmengemudidb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class customeradapter extends BaseAdapter {
    Activity activity;
    List<data> items;
    private LayoutInflater inflater;

    public customeradapter(Activity activity, List<data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.list, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView Nama = (TextView) convertView.findViewById(R.id.Nama);
        TextView Telepon = (TextView) convertView.findViewById(R.id.Telepon);
        TextView Tanggal_daftar = (TextView) convertView.findViewById(R.id.Tanggal_daftar);
        TextView Paket = (TextView) convertView.findViewById(R.id.Paket);
        TextView Jenis_mobil = (TextView) convertView.findViewById(R.id.Jenis_mobil);
        TextView Harga = (TextView) convertView.findViewById(R.id.Harga);

        data Data = items.get(position);

        id.setText(Data.getId());
        Nama.setText(Data.getNama());
        Telepon.setText(Data.getTelepon());
        Tanggal_daftar.setText(Data.getTanggal_daftar());
        Paket.setText(Data.getPaket());
        Jenis_mobil.setText(Data.getJenis_mobil());
        Harga.setText(Data.getHarga());

        return convertView;
    }
}