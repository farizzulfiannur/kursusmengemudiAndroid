package com.example.kursusmengemudidb;

public class data {
    private String id,Nama,Telepon,Tanggal_daftar,Paket,Jenis_mobil,Harga;

    public data() {

    }

    public data (String id, String Nama, String Telepon, String Tanggal_daftar, String Paket, String Jenis_mobil, String Harga){
        this.id =id;
        this.Nama = Nama;
        this.Telepon = Telepon;
        this.Tanggal_daftar = Tanggal_daftar;
        this.Paket = Paket;
        this.Jenis_mobil = Jenis_mobil;
        this.Harga = Harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getTelepon() {
        return Telepon;
    }

    public void setTelepon(String telepon) {
        Telepon = telepon;
    }

    public String getTanggal_daftar() {
        return Tanggal_daftar;
    }

    public void setTanggal_daftar(String tanggal_daftar) {
        Tanggal_daftar = tanggal_daftar;
    }

    public String getPaket() {
        return Paket;
    }

    public void setPaket(String paket) {
        Paket = paket;
    }

    public String getJenis_mobil() {
        return Jenis_mobil;
    }

    public void setJenis_mobil(String jenis_mobil) {
        Jenis_mobil = jenis_mobil;
    }

    public String getHarga() {
        return Harga;
    }

    public void setHarga(String harga) {
        Harga = harga;
    }
}
