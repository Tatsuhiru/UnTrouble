package com.example.untrouble;

public class SearchData {
    private int img;
    private String judul, deskripsi, deskripsi2, minidesk, index;

    public SearchData(int img, String judul, String deskripsi, String deskripsi2, String minidesk, String index) {
        this.img = img;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deskripsi2 = deskripsi2;
        this.minidesk = minidesk;
        this.index = index;
    }

    public int getImg() {return img;}
    public String getJudul() {return judul;}
    public String getDeskripsi() {return deskripsi;}
    public String getDeskripsi2() {return deskripsi2;}
    public String getMinidesk() {return minidesk;}
    public String getIndex() {return index;}
}
