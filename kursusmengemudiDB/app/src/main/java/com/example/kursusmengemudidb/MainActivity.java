package com.example.kursusmengemudidb;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String URLSELECT = "http://192.168.1.5/kursusmengemudiPHP/select.php";//cari menggunakan ipconfiq melalui CMD
    public static final String URLDELETE = "http://192.168.1.5/kursusmengemudiPHP/delete.php";
    public static final String URLEDIT = "http://192.168.1.5/kursusmengemudiPHP/edit.php";
    public static final String URLINSERT = "http://192.168.1.5/kursusmengemudiPHP/insert.php";
    ListView list;
    SwipeRefreshLayout swipe;
    List<data> itemList = new ArrayList<data>();
    customeradapter adapter;
    RadioGroup rg_paket,rg_jenis_mobil;
    RadioButton rb_pilihpaket,rb_pilihjenis,rb_belajarr,rb_mengulangg,rb_manuall,rb_maticc;
    LayoutInflater inflater;
    EditText tid,tNama,tTelepon,tTanggal_daftar,tHarga;
    String vid,vNama,vTelepon,vTanggal_daftar,vPaket,vJenis_mobil,vHarga;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        list = (ListView) findViewById(R.id.list);

        adapter = new customeradapter(MainActivity.this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogForm("","","","","","","","Tambah");

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final CharSequence[] pilihanAksi = {"Hapus","Ubah"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(pilihanAksi, new DialogInterface.OnClickListener(){
                    public void onClick (DialogInterface dialog,int which){
                        switch (which){
                            case 0 :
                            hapusData(idx);
                            break;

                            case 1 :
                            ubahData(idx);
                            break;
                        }
                    }
                }).show();
            return false;
            }
        });


    }
    public void ubahData(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String idx = jObj.getString("id");
                            String Namax = jObj.getString("Nama");
                            String Teleponx = jObj.getString("Telepon");
                            String Tanggal_daftarx = jObj.getString("Tanggal_daftar");
                            String Paketx = jObj.getString("Paket");
                            String Jenis_mobilx = jObj.getString("Jenis_mobil");
                            String Hargax = jObj.getString("Harga");

                            dialogForm(idx,Namax, Teleponx, Tanggal_daftarx, Paketx, Jenis_mobilx, Hargax, "UPDATE");

                            adapter.notifyDataSetChanged();

                        }catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Gagal Koneksi Ke server, Cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id);

                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    public void dialogForm(String id, String Nama, String Telepon, String Tanggal_daftar, String Paket, String Jenis_mobil, String Harga, String button){
        AlertDialog.Builder dialogForm = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.form_customer, null);
        dialogForm.setView(viewDialog);
        dialogForm.setCancelable(true);
        dialogForm.setTitle("Formulir Pemesanan");

        tid = (EditText) viewDialog.findViewById(R.id.inId);
        tNama = (EditText) viewDialog.findViewById(R.id.inNama);
        tTelepon = (EditText) viewDialog.findViewById(R.id.inTelepon);
        tTanggal_daftar = (EditText) viewDialog.findViewById(R.id.inTanggal_daftar);

        rg_paket = (RadioGroup) viewDialog.findViewById(R.id.rg_paket);
        rb_belajarr = (RadioButton) viewDialog.findViewById(R.id.rb_belajar);
        rb_mengulangg = (RadioButton) viewDialog.findViewById(R.id.rb_mengulang);

        rg_jenis_mobil = (RadioGroup) viewDialog.findViewById(R.id.rg_jenis_mobil);
        rb_manuall = (RadioButton) viewDialog.findViewById(R.id.rb_manual);
        rb_maticc = (RadioButton) viewDialog.findViewById(R.id.rb_matic);
        tHarga = (EditText) viewDialog.findViewById(R.id.inHarga);

        if (id.isEmpty()){
            tid.setText(null);
            tNama.setText(null);
            tTelepon.setText(null);
            tTanggal_daftar.setText(null);
            tHarga.setText(null);
        }else{
            tid.setText(id);
            tNama.setText(Nama);
            tTelepon.setText(Telepon);
            tTanggal_daftar.setText(Tanggal_daftar);

            if (Paket.equals("Belajar")){
                rb_belajarr.setChecked(true);

            }
            else if (Paket.equals("Mengulang")){
                rb_mengulangg.setChecked(true);

            }
            if (Jenis_mobil.equals("Manual")){
                rb_manuall.setChecked(true);
            }
            else {
                rb_maticc.setChecked(true);
            }
            tHarga.setText(Harga);
        }

        dialogForm.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vid = tid.getText().toString();
                vNama = tNama.getText().toString();
                vTelepon = tTelepon.getText().toString();
                vTanggal_daftar = tTanggal_daftar.getText().toString();

                int paket_terpilih = rg_paket.getCheckedRadioButtonId();
                rb_pilihpaket = (RadioButton) viewDialog.findViewById(paket_terpilih);
                vPaket =  rb_pilihpaket.getText().toString();

                int jenismobil_terpilih = rg_jenis_mobil.getCheckedRadioButtonId();
                rb_pilihjenis = (RadioButton) viewDialog.findViewById(jenismobil_terpilih);
                vJenis_mobil= rb_pilihjenis.getText().toString();
                vHarga= tHarga.getText().toString();

                simpan();
                dialog.dismiss();
            }
        });
        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                tid.setText(null);
                tNama.setText(null);
                tTelepon.setText(null);
                tTanggal_daftar.setText(null);
                tHarga.setText(null);
            }
        });
        dialogForm.show();

    }
    public void simpan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLINSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                if (vid.isEmpty()) {
                    params.put("Nama", vNama);
                    params.put("Telepon", vTelepon);
                    params.put("Tanggal_daftar", vTanggal_daftar);
                    params.put("Paket", vPaket);
                    params.put("Jenis_mobil", vJenis_mobil);
                    params.put("Harga", vHarga);
                    return params;
                } else {
                    params.put("id", vid);
                    params.put("Nama", vNama);
                    params.put("Telepon", vTelepon);
                    params.put("Tanggal_daftar", vTanggal_daftar);
                    params.put("Paket", vPaket);
                    params.put("Jenis_mobil", vJenis_mobil);
                    params.put("Harga", vHarga);
                    return params;
                }
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    public void hapusData(String id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLDELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        callVolley();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Gagal Koneksi Ke server, Cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id);

                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }
    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(URLSELECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        data item = new data();

                        item.setId(obj.getString("id"));
                        item.setNama(obj.getString("Nama"));
                        item.setTelepon(obj.getString("Telepon"));
                        item.setTanggal_daftar(obj.getString("Tanggal_daftar"));
                        item.setPaket(obj.getString("Paket"));
                        item.setJenis_mobil(obj.getString("Jenis_mobil"));
                        item.setHarga(obj.getString("Harga"));
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);

    }
}