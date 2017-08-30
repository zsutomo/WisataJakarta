package com.skripsi.zulfallah.wisatajakarta;

/**
 * Created by jaka on 8/26/17.
 */

public class ModelWisata {

    String nama;
    String alamat;
    String tanggal;
    String jam;
    int ImageResourceID;

    public int getImageResourceID() {
        return ImageResourceID;
    }

    public void setImageResourceID(int imageResourceID) {
        ImageResourceID = imageResourceID;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
}
