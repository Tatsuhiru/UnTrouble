package com.example.untrouble;

public class SearchData {
    private int img;
    private String judul, deskripsi, deskripsi2, minidesk;

    public SearchData(int img, String judul, String deskripsi, String deskripsi2, String minidesk) {
        this.img = img;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deskripsi2 = deskripsi2;
        this.minidesk = minidesk;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi2(){
        return deskripsi2;
    }

    public void setDeskripsi2(String deskripsi2) {
        this.deskripsi2 = deskripsi2;
    }

    public String getMinidesk(){
        return minidesk;
    }

    public void setMinidesk(String minidesk){
        this.minidesk = minidesk;
    }
}
