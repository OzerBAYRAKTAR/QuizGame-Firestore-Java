package com.example.egiticimatematikoyunlari.Models;

import java.io.Serializable;

public class KategoriModelClass implements Serializable {

    private String kategoriId;
    private String kategori_ad;
    private String kategori_resim;

    public KategoriModelClass() {
    }

    public KategoriModelClass(String kategoriId, String kategori_ad, String kategori_resim) {
        this.kategoriId = kategoriId;
        this.kategori_ad = kategori_ad;
        this.kategori_resim = kategori_resim;
    }

    public String getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(String kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategori_ad() {
        return kategori_ad;
    }

    public void setKategori_ad(String kategori_ad) {
        this.kategori_ad = kategori_ad;
    }

    public String getKategori_resim() {
        return kategori_resim;
    }

    public void setKategori_resim(String kategori_resim) {
        this.kategori_resim = kategori_resim;
    }
}
